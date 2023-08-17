package com.epam.mobitru.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import timber.log.Timber

open class ToastHandlerImpl<F> : ToastHandler<F> where F : Fragment, F : WithViewModel<*> {
    private lateinit var fragment: F
    override fun subscribe(fragment: F) {
        this.fragment = fragment
        with(fragment) {
            lifecycleScope.launchWhenResumed {
                viewModel.toast.collect {
                    showToast(it)
                }
            }
        }
    }

    protected open fun showToast(message: String) {
        Timber.d("Showing toast: $message")
        Toast.makeText(fragment.requireContext(), message, Toast.LENGTH_LONG).show()
    }
}