package com.epam.mobitru.models

data class PaymentDetails(
    val packagingFee: Double,
    val subtotal: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
)