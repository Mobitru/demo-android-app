package com.epam.mobitru.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderItem(
    val id: String,
    val products: List<ProductWithQuantityItem>,
    val user: LoginResponse,
    val paymentDetails: PaymentDetailsItem,
    val state: OrderStateItem,
)
