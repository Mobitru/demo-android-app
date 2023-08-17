package com.epam.mobitru.base

import androidx.fragment.app.Fragment

interface FragmentVmHandler<F> where F : Fragment, F : WithViewModel<*> {
    fun subscribe(fragment: F)
}