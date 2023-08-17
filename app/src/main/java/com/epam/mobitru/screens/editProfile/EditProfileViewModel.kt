package com.epam.mobitru.screens.editProfile

import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.base.navigateBack
import com.epam.mobitru.models.User
import com.epam.mobitru.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    companion object {
        const val FIELD_IS_EMPTY_ERROR = "Field can’t be empty"
    }

    private val _firstName: MutableStateFlow<String> =
        MutableStateFlow(userRepository.currentUser.value?.firstName ?: "")
    val firstName: StateFlow<String> = _firstName.asStateFlow()
    private val _firstNameError: MutableStateFlow<String> = MutableStateFlow("")
    val firstNameError: StateFlow<String> = _firstNameError.asStateFlow()

    private val _lastName: MutableStateFlow<String> =
        MutableStateFlow(userRepository.currentUser.value?.lastName ?: "")
    val lastName: StateFlow<String> = _lastName.asStateFlow()
    private val _lastNameError: MutableStateFlow<String> = MutableStateFlow("")
    val lastNameError: StateFlow<String> = _lastNameError.asStateFlow()

    val email: StateFlow<String> =
        userRepository.currentUser.map { it?.email ?: "BBB" }
            .stateIn(viewModelScope, SharingStarted.Lazily, "AAA")

//    val email: Flow<String> = userRepository.currentUser.map { it?.email ?: "BBB" }

    private val _address: MutableStateFlow<String> =
        MutableStateFlow(userRepository.currentUser.value?.address ?: "")
    val address: StateFlow<String> = _address.asStateFlow()
    private val _addressError: MutableStateFlow<String> = MutableStateFlow("")
    val addressError: StateFlow<String> = _addressError.asStateFlow()

    fun onFirstNameUpdated(value: String) {
        viewModelScope.launch {
            _firstName.emit(value)
            _firstNameError.emit("")
        }
    }

    fun onLastNameUpdated(value: String) {
        viewModelScope.launch {
            _lastName.emit(value)
            _lastNameError.emit("")
        }
    }

    fun onAddressUpdated(value: String) {
        viewModelScope.launch {
            _address.emit(value)
            _addressError.emit("")
        }
    }

    fun save() {
        viewModelScope.launch {
            var isErrorPresented = false
            if (TextUtils.isEmpty(_firstName.value)) {
                _firstNameError.emit(FIELD_IS_EMPTY_ERROR)
                isErrorPresented = true
            }
            if (TextUtils.isEmpty(_lastName.value)) {
                _lastNameError.emit(FIELD_IS_EMPTY_ERROR)
                isErrorPresented = true
            }
            if (TextUtils.isEmpty(_address.value)) {
                _addressError.emit(FIELD_IS_EMPTY_ERROR)
                isErrorPresented = true
            }
            if (isErrorPresented) {
                return@launch
            }
            userRepository.updateUser(
                User(
                    firstName.value,
                    lastName.value,
                    email.value,
                    address.value
                )
            )
            navigateBack()
        }
    }
}