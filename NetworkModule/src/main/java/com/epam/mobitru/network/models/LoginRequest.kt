package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(val login: String, val password: String)