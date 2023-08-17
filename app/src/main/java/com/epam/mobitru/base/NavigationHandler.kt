package com.epam.mobitru.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

interface NavigationHandler<F> : FragmentVmHandler<F> where F : Fragment, F : WithViewModel<*> {
    fun navigate(direction: NavCommand)

    fun findController(): NavController
}