package com.epam.mobitru.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber

class CartRepository {

    private val _currentCartState: MutableStateFlow<Map<String, Int>> =
        MutableStateFlow(emptyMap())

    private val _cartPromo: MutableStateFlow<String?> = MutableStateFlow(null)
    val cartPromo = _cartPromo.asStateFlow()

    val currentCart = _currentCartState.asStateFlow()

    val positionsInCart = _currentCartState.map { it.size }

    suspend fun applyPromo(value: String) {
        _cartPromo.emit(value)
    }

    suspend fun removePromo() {
        _cartPromo.emit(null)
    }

    fun add(itemId: String, itemCount: Int) {
        Timber.i("Add to cart $itemId: $itemCount")
        _currentCartState.update {
            it + (itemId to itemCount)
        }
    }

    fun isAdded(itemId: String): Boolean {
        return _currentCartState.value.containsKey(itemId)
    }

    fun remove(itemId: String) {
        Timber.i("remove from cart $itemId")
        _currentCartState.update {
            it - itemId
        }
    }

    fun cleanCart() {
        Timber.i("Clean cart")
        _currentCartState.update {
            emptyMap()
        }
    }

    suspend fun logout() {
        _cartPromo.emit(null)
        _currentCartState.emit(emptyMap())
    }
}