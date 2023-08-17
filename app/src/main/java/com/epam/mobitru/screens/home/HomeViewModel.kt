package com.epam.mobitru.screens.home

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.repository.CartRepository
import com.epam.mobitru.repository.ProductsRepository
import com.epam.mobitru.repository.RepositoryError
import com.epam.mobitru.screens.home.sort.Direction
import com.epam.mobitru.screens.home.sort.ProductSortKind
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository,
) : BaseViewModel() {

    private var lastChangedItemId : String? = null

    val list: Flow<List<Group>> =
        combine(cartRepository.currentCart, productsRepository.currentProducts) { _, products ->
            products?.map {
                val isAdded = cartRepository.isAdded(it.id)
                ProductViewItem(
                    it,
                    isAdded,
                    lastChangedItemId == it.id,
                    onAddToCartClick = {
                        lastChangedItemId = it.id
                        if (isAdded) {
                            cartRepository.remove(it.id)
                        } else {
                            cartRepository.add(it.id, 1)
                        }
                    }
                ) {
                    sendToast("Product details screen not implemented")
                }
            } ?: emptyList()
        }

    val title: Flow<String> = list.map { "Mobile phones (${it.size})" }
    val selectedOrder: StateFlow<Pair<ProductSortKind, Direction>> =
        productsRepository.selectedOrder

    init {
        viewModelScope.launch {
            try {
                productsRepository.getProductsList()
            } catch (e: RepositoryError) {
                Timber.e(e)
            }
        }
    }
}