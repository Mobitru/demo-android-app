package com.epam.mobitru.mappers

import com.epam.mobitru.models.Order
import com.epam.mobitru.models.OrderState
import com.epam.mobitru.models.PaymentDetails
import com.epam.mobitru.models.ProductWithQuantity
import com.epam.mobitru.network.models.OrderItem
import com.epam.mobitru.network.models.PaymentDetailsItem
import com.epam.mobitru.network.models.ProductWithQuantityItem

class OrderMapper (
    private val userMapper: UserMapper,
    private val productMapper: ProductMapper,
) {
    private fun map(original: PaymentDetailsItem): PaymentDetails {
        return PaymentDetails(
            packagingFee = original.packagingFee,
            subtotal = original.subtotal,
            deliveryFee = original.deliveryFee,
            discount = original.discount,
            total = original.total
        )
    }

    fun map(original: OrderItem): Order {
        return Order(
            id = original.id,
            products = mapQuantityList(original.products),
            user = userMapper.map(original.user),
            paymentDetails = map(original.paymentDetails),
            state = OrderState.valueOf(original.state.name),
        )
    }

    private fun mapQuantityList(products: List<ProductWithQuantityItem>): List<ProductWithQuantity> {
        return products.map {
            ProductWithQuantity(
                product = productMapper.map(it.product),
                quantity = it.quantity,
            )
        }
    }

    fun map(rawOrders: List<OrderItem>): List<Order> {
        return rawOrders.map { map(it) }
    }

}