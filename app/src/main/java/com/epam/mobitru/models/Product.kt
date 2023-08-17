package com.epam.mobitru.models

import android.text.TextUtils

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val discountPrice: Double?,
    val discountValue: String?,
    val image: String?,
) {
    val isDiscountExist = !TextUtils.isEmpty(discountValue)

    val assetPath = "file:///android_asset/images/${image}.webp"
}