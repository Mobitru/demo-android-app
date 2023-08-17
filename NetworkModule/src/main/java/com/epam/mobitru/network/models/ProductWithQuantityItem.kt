package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductWithQuantityItem(
    val product: ProductItem,
    val quantity: Int,
)
