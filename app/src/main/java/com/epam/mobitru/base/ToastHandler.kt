package com.epam.mobitru.base

import androidx.fragment.app.Fragment

interface ToastHandler<F> : FragmentVmHandler<F> where F : Fragment, F : WithViewModel<*>