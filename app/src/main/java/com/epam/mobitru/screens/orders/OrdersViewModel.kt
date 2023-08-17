package com.epam.mobitru.screens.orders

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.base.navigate
import com.epam.mobitru.main.MainFragmentDirections
import com.epam.mobitru.models.OrderState
import com.epam.mobitru.repository.OrderRepository
import com.epam.mobitru.repository.RepositoryError
import com.epam.mobitru.views.listview.SectionViewItem
import com.epam.mobitru.views.listview.WhiteSectionViewItem
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class OrdersViewModel(
    ordersRepository: OrderRepository,
) : BaseViewModel() {

    init {
        Timber.e("OrdersViewModel")
        viewModelScope.launch {
            try {
                ordersRepository.getOrdersList()
            } catch (e: RepositoryError) {
                Timber.e(e)
            }
        }
    }

    val list: Flow<List<Group>> = ordersRepository.orders.map { ordersList ->
        val ordersInProgress = ordersList.filter { it.state == OrderState.IN_PROGRESS }
        val ordersCompleted = ordersList.filter { it.state == OrderState.DONE }
        val resultList = mutableListOf<Group>()
        if (ordersInProgress.isNotEmpty()) {
            resultList += WhiteSectionViewItem("In Progress (${ordersInProgress.size})")
            resultList += ordersInProgress.map {
                OrderViewItem(it) {
                    navigate(
                        MainFragmentDirections.actionMainFragmentToShowOrderFragment(it.id)
                    )
                }
            }
        }
        if (ordersCompleted.isNotEmpty()) {
            resultList += SectionViewItem("Completed (${ordersCompleted.size})")
            resultList += ordersCompleted.map {
                OrderViewItem(it) {
                    navigate(
                        MainFragmentDirections.actionMainFragmentToShowOrderFragment(it.id)
                    )
                }
            }
        }
        resultList
    }

}