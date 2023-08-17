package com.epam.mobitru.repository

import com.epam.mobitru.mappers.OrderMapper
import com.epam.mobitru.models.Order
import com.epam.mobitru.models.OrderState
import com.epam.mobitru.models.PaymentDetails
import com.epam.mobitru.models.ProductWithQuantity
import com.epam.mobitru.network.RetrofitApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import timber.log.Timber

class OrderRepository(
    private val api: RetrofitApi,
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val ordersMapper: OrderMapper,
) {


    val getDraftOrder: Flow<Order> =
        combine(
            productsRepository.currentProducts,
            cartRepository.currentCart,
            userRepository.currentUser
        ) { products, cartItems, user ->
            Timber.d("Preparing order")
            if (products == null) {
                throw RepositoryError("Can not create order without products", RuntimeException())
            }
            val productWithQuantities = cartItems.map { cartItem ->
                ProductWithQuantity(products.find { it.id == cartItem.key }!!, cartItem.value)
            }
            if (user == null) {
                throw MissedUserError("Please provide user details", RuntimeException())
            }
            Order(
                "",
                productWithQuantities,
                user,
                calculatePaymentDetails(productWithQuantities),
                state = OrderState.NEW
            )
        }

    private val _orders = MutableStateFlow(emptyList<Order>())
    private val _ordersLocal = MutableStateFlow(emptyList<Order>())
    val orders = combine(_orders, _ordersLocal) { o1, o2 ->
        o1 + o2
    }

    suspend fun completeDraftOrder() {
        Timber.d("Completing order")
        val order = getDraftOrder.first()
        Timber.d("Order in progress: $order")
        _ordersLocal.emit(
            listOf(
                order.copy(
                    id = "12131415",
                    state = OrderState.IN_PROGRESS
                )
            )
        )
        cartRepository.cleanCart()
        Timber.d("Completing order")
    }

    suspend fun getOrdersList() {
        Timber.i("getOrdersList")
        try {
            val rawOrders = api.getOrdersList()
            _orders.emit(ordersMapper.map(rawOrders))
            Timber.i("getOrdersList success")
        } catch (e: Exception) {
            Timber.e(e, "getOrdersList failed")
            _orders.emit(emptyList())
            throw RepositoryError("getOrdersList failed", e)
        }
    }

    suspend fun logout() {
        _orders.emit(emptyList())
        _ordersLocal.emit(emptyList())
    }

    private fun calculatePaymentDetails(products: List<ProductWithQuantity>): PaymentDetails {
        val (fullPriceSum, discountPriceSum) = products.fold(0.0 to 0.0) { (accPrice, accDiscountPrice), (product, count) ->
            accPrice + product.price * count to accDiscountPrice + (product.discountPrice
                ?: product.price) * count
        }
        val packagingFee = 0.29
        val deliveryFee = 11.50
        return PaymentDetails(
            packagingFee = packagingFee,
            deliveryFee = deliveryFee,
            subtotal = fullPriceSum,
            discount = fullPriceSum - discountPriceSum,
            total = discountPriceSum + packagingFee + deliveryFee,
        )
    }
}