package com.epam.mobitru.repository

import com.epam.mobitru.mappers.ProductMapper
import com.epam.mobitru.models.Product
import com.epam.mobitru.network.RetrofitApi
import com.epam.mobitru.screens.home.sort.Direction
import com.epam.mobitru.screens.home.sort.ProductSortKind
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import timber.log.Timber

class ProductsRepository(
    private val api: RetrofitApi,
    private val productMapper: ProductMapper,
) {

    companion object {
        val DEFAULT_SORT = ProductSortKind.ByPrice to Direction.Asc
    }


    private val _currentProducts: MutableStateFlow<List<Product>?> = MutableStateFlow(null)
    private val _selectedOrder = MutableStateFlow(DEFAULT_SORT)

    val selectedOrder = _selectedOrder.asStateFlow()

    val currentProducts = combine(_currentProducts, _selectedOrder) { productList, order ->
        if (productList == null) return@combine null
        when (order) {
            ProductSortKind.ByPrice to Direction.Asc -> productList.sortedBy {
                it.discountPrice ?: it.price
            }
            ProductSortKind.ByPrice to Direction.Desc -> productList.sortedByDescending {
                it.discountPrice ?: it.price
            }
            ProductSortKind.ByName to Direction.Asc -> productList.sortedBy { it.name }
            ProductSortKind.ByName to Direction.Desc -> productList.sortedByDescending { it.name }
            else -> productList
        }
    }

    suspend fun applySortOrder(order: Pair<ProductSortKind, Direction>) {
        _selectedOrder.emit(order)
    }

    suspend fun getProductsList() {
        try {
            val rawProducts = api.getProductsList()
            _currentProducts.emit(productMapper.map(rawProducts))
            Timber.i("getProductsList success")
        } catch (e: Exception) {
            Timber.e(e, "getProductsList failed")
            _currentProducts.emit(null)
            throw RepositoryError("getProductsList failed", e)
        }
    }

    suspend fun logout() {
        _currentProducts.emit(null)
        _selectedOrder.emit(DEFAULT_SORT)
    }
}