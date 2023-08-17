package com.epam.mobitru.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.epam.mobitru.extentions.throttleFirst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class VmNavigator {

    companion object {
        const val NAVIGATION_THROTTLE = 400L
    }

    private val _navigation = MutableSharedFlow<NavCommand>()

    val navigation: Flow<NavCommand> =
        _navigation.throttleFirst(NAVIGATION_THROTTLE)

    fun navigate(vm: ViewModel, destination: NavDirections) {
        navigate(vm, NavCommand.Direction(destination))
    }

    fun navigate(vm: ViewModel, destination: NavCommand) {
        vm.viewModelScope.launch {
            Timber.i("Navigating to $destination")
            _navigation.emit(destination)
        }
    }
}

fun BaseViewModel.navigate(destination: NavCommand) {
    navigator.navigate(this, destination)
}

fun BaseViewModel.navigate(destination: NavDirections) {
    navigate(NavCommand.Direction(destination))
}

fun BaseViewModel.navigateBack() {
    navigate(NavCommand.Back)
}