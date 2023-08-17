package com.epam.mobitru.mappers

import com.epam.mobitru.models.User
import com.epam.mobitru.network.models.LoginResponse

class UserMapper {
    fun map(rawUser: LoginResponse): User {
        return User(
            firstName = rawUser.firstName,
            lastName = rawUser.lastName,
            email = rawUser.email,
            address = rawUser.address,
        )
    }
}