package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductItem(
    val id: String,
    val name: String,
    val price: Double,
    val discountPrice: Double?,
    val discountValue: String?,
    val image: String?,
)