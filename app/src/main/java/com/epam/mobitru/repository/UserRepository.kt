package com.epam.mobitru.repository

import com.epam.mobitru.mappers.UserMapper
import com.epam.mobitru.models.Credentials
import com.epam.mobitru.models.User
import com.epam.mobitru.network.RetrofitApi
import com.epam.mobitru.network.models.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class UserRepository(
    private val api: RetrofitApi,
    private val userMapper: UserMapper,
) {

    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)

    val currentUser = _currentUser.asStateFlow()
    val isSignedIn = _currentUser.map { it != null }

    suspend fun updateUser(newUser: User) {
        _currentUser.emit(newUser)
    }

    suspend fun signIn(creds: Credentials) {
        Timber.i("signIn")
        try {
            val rawUser = api.login(LoginRequest(creds.email, creds.password))
            _currentUser.emit(userMapper.map(rawUser))
            Timber.i("signIn success")
        } catch (e: Exception) {
            Timber.e(e, "signIn failed")
            _currentUser.emit(null)
            throw RepositoryError("Sign in failed", e)
        }
    }

    suspend fun logout() {
        Timber.i("logout")
        _currentUser.emit(null)
    }

    suspend fun signInBio() {
        Timber.i("signIn bio")
        try {
            val rawUser = api.loginBio()
            _currentUser.emit(userMapper.map(rawUser))
            Timber.i("signInBio success")
        } catch (e: Exception) {
            Timber.e(e, "signInBio failed")
            _currentUser.emit(null)
            throw RepositoryError("SignBio in failed", e)
        }
    }
}