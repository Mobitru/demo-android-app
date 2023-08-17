package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentDetailsItem(
    val packagingFee: Double,
    val subtotal: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
)