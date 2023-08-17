package com.epam.mobitru.screens.cart

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.repository.CartRepository
import com.epam.mobitru.repository.ProductsRepository
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository,
) : BaseViewModel() {
    fun applyPromo(value: String) {
        viewModelScope.launch {
            cartRepository.applyPromo(value)
        }
    }

    fun removePromo() {
        viewModelScope.launch {
            cartRepository.removePromo()
        }
    }

    val list: StateFlow<List<Group>> =
        combine(
            productsRepository.currentProducts.filterNotNull(),
            cartRepository.currentCart
        ) { products, cartItems ->
            cartItems.map { cartItem ->
                (products.first { it.id == cartItem.key }) to cartItem.value
            }
                .map { (product, count) ->
                    CartViewItem(
                        product,
                        count
                    ) { action ->
                        when (action) {
                            CartViewItem.Actions.PLUS -> cartRepository.add(
                                product.id,
                                count + 1
                            )
                            CartViewItem.Actions.MINUS -> cartRepository.add(
                                product.id,
                                count - 1
                            )
                            CartViewItem.Actions.REMOVE -> cartRepository.remove(product.id)
                        }
                    }
                }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val cartPromo = cartRepository.cartPromo
}