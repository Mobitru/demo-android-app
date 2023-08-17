package com.epam.mobitru.models

data class Order(
    val id: String,
    val products: List<ProductWithQuantity>,
    val user: User,
    val paymentDetails: PaymentDetails,
    val state: OrderState,
)
