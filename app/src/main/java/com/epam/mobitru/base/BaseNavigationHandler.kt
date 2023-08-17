package com.epam.mobitru.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import timber.log.Timber

abstract class BaseNavigationHandler<F> :
    NavigationHandler<F> where F : Fragment, F : WithViewModel<*> {
    protected lateinit var fragment: F
    override fun subscribe(fragment: F) {
        this.fragment = fragment
        with(fragment) {
            lifecycleScope.launchWhenResumed {
                viewModel.navigator.navigation.collect {
                    Timber.i("Navigating to $it")
                    navigate(it)
                }
            }
        }
    }
}