package com.epam.mobitru.base

interface WithViewModel<VM : BaseViewModel> {
    val viewModel: VM
}