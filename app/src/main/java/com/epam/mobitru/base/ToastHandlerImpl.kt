package com.epam.mobitru.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import timber.log.Timber

open class ToastHandlerImpl<F> : ToastHandler<F> where F : Fragment, F : WithViewModel<*> {
    private lateinit var fragment: F
    override fun subscribe(fragment: F) {
        this.fragment = fragment
        with(fragment) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.toast.collect {
                        showToast(it)
                    }
                }
            }
        }
    }

    protected open fun showToast(message: String) {
        Timber.d("Showing toast: $message")
        Toast.makeText(fragment.requireContext(), message, Toast.LENGTH_LONG).show()
    }
}