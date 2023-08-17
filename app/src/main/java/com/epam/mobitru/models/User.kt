package com.epam.mobitru.models

import android.text.TextUtils

data class User(
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val address: String?,
) {
    val fullName
        get() = "${firstName ?: ""} ${lastName ?: ""}"
    val isAllFieldsFilled =
        !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(
            address
        )
}