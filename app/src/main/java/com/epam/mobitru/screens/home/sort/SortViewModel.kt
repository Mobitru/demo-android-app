package com.epam.mobitru.screens.home.sort

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SortViewModel(
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    private val _selectedOrder = MutableStateFlow(productsRepository.selectedOrder.value)

    companion object {
        val SORT_OPTIONS = mapOf(
            R.string.sort_by_price_asc to (ProductSortKind.ByPrice to Direction.Asc),
            R.string.sort_by_price_des to (ProductSortKind.ByPrice to Direction.Desc),
            R.string.sort_by_name_asc to (ProductSortKind.ByName to Direction.Asc),
            R.string.sort_by_name_des to (ProductSortKind.ByName to Direction.Desc),
        )
    }

    val list = _selectedOrder.map { order ->
        SORT_OPTIONS.map { (nameResId, sortPair) ->
            SortViewItem(
                nameResId,
                isActive = order == sortPair,
            ) {
                viewModelScope.launch {
                    _selectedOrder.emit(sortPair)
                }
            }
        }
    }

    fun apply() {
        viewModelScope.launch {
            productsRepository.applySortOrder(_selectedOrder.value)
        }
    }

}