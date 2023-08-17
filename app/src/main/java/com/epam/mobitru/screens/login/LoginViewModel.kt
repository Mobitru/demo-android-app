package com.epam.mobitru.screens.login

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.models.Credentials
import com.epam.mobitru.repository.RepositoryError
import com.epam.mobitru.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    sealed class State {
        object Default : State()
        object Error : State()
        object Progress : State()
        object Success : State()
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Default)
    val state: Flow<State> = _state.asStateFlow()
    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailUpdated(value: String) {
        viewModelScope.launch {
            _email.emit(value)
        }
        onCloseError()
    }

    fun onPasswordUpdated(value: String) {
        viewModelScope.launch {
            _password.emit(value)
        }
        onCloseError()
    }

    fun login() {
        viewModelScope.launch {
            _state.emit(State.Progress)
            try {
                userRepository.signIn(Credentials(email.value, password.value))
                _state.emit(State.Success)
            } catch (e: RepositoryError) {
                Timber.e(e, "Login failed")
                _state.emit(State.Error)
            }
        }
    }

    fun onCloseError() {
        viewModelScope.launch {
            _state.emit(State.Default)
        }
    }

    fun onBioAuth() {
        viewModelScope.launch {
            _state.emit(State.Progress)
            try {
                userRepository.signInBio()
                _state.emit(State.Success)
            } catch (e: RepositoryError) {
                Timber.e(e, "BioLogin failed")
                _state.emit(State.Error)
            }
        }
    }

}