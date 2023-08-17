package com.epam.mobitru.main

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    val cartCounter =
        cartRepository.positionsInCart.stateIn(viewModelScope, SharingStarted.Lazily, 0)

}