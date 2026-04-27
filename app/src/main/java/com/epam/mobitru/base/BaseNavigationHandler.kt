package com.epam.mobitru.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseNavigationHandler<F> :
    NavigationHandler<F> where F : Fragment, F : WithViewModel<*> {
    protected lateinit var fragment: F
    override fun subscribe(fragment: F) {
        this.fragment = fragment
        with(fragment) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.navigator.navigation.collect {
                        Timber.i("Navigating to $it")
                        navigate(it)
                    }
                }
            }
        }
    }
}