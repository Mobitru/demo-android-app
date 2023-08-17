package com.epam.mobitru.util

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.epam.mobitru.BuildConfig
import timber.log.Timber
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T,
    private val mode: Mode
) : ReadOnlyProperty<Fragment, T?> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(InitialLifecycleObserver())
    }

    inner class InitialLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                viewLifecycleOwner.lifecycle.addObserver(LiveDestroyObserver(viewLifecycleOwner))
            }
            fragment.lifecycle.removeObserver(this)
        }
    }

    inner class LiveDestroyObserver(private val viewLifecycleOwner: LifecycleOwner) :
        DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
            viewLifecycleOwner.lifecycle.removeObserver(this)
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return binding ?: let {
            val lifecycle: Lifecycle
            try {
                lifecycle = fragment.viewLifecycleOwner.lifecycle
            } catch (e: IllegalStateException) {
                Timber.e(e, "Can not get lifecycle for view binding")
                return null
            }
            if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                return reportError()
            }

            return when (mode) {
                Mode.ROOT_VIEW -> viewBindingFactory(thisRef.requireView()).also {
                    this.binding = it
                }
            }
        }
    }

    private fun reportError(): T? {
        if (BuildConfig.DEBUG) {
            error("Should not attempt to get bindings when Fragment views are destroyed.")
        } else {
            Timber.e("Should not attempt to get bindings when Fragment views are destroyed.")
            return null
        }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T,
    mode: Mode = Mode.ROOT_VIEW
) = FragmentViewBindingDelegate(this, viewBindingFactory, mode)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

enum class Mode {
    ROOT_VIEW,
}