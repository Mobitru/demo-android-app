package com.epam.mobitru.screens.showOrder

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.repository.OrderRepository
import com.epam.mobitru.screens.reviewOrder.OrderViewItem
import com.epam.mobitru.views.listview.PaymentDetailsViewItem
import com.epam.mobitru.views.listview.SectionViewItem
import com.epam.mobitru.views.listview.UserViewItem
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShowOrderViewModel(
    private val args: ShowOrderFragmentArgs,
    private val orderRepository: OrderRepository,
) : BaseViewModel() {

    private val _orderId = MutableStateFlow(args.orderId)
    val orderId = _orderId.asStateFlow()

    fun readArgs(args: ShowOrderFragmentArgs) {
        viewModelScope.launch {
            _orderId.emit(args.orderId)
        }
    }

    val list: Flow<List<Group>> =
        combine(orderRepository.orders, _orderId) { listOfOrders, orderId ->
            listOfOrders.first { it.id == orderId }
        }
            .map { order ->
                order.products.map {
                    OrderViewItem(it.product, it.quantity)
                } + SectionViewItem(R.string.contact_details) +
                        UserViewItem(order.user) + SectionViewItem(R.string.payment_details) + PaymentDetailsViewItem(
                    order.paymentDetails
                )
            }
}