package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val address: String?,
)