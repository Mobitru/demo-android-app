package com.epam.mobitru.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val navigator = VmNavigator()
    private val _toast = MutableSharedFlow<String>()
    val toast: Flow<String> = _toast.asSharedFlow()

    protected fun sendToast(message: String) {
        viewModelScope.launch {
            Timber.v("Toast $message")
            _toast.emit(message)
        }
    }
}