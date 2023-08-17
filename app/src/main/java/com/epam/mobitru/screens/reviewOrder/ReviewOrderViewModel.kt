package com.epam.mobitru.screens.reviewOrder

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.navigate
import com.epam.mobitru.repository.OrderRepository
import com.epam.mobitru.views.listview.PaymentDetailsViewItem
import com.epam.mobitru.views.listview.SectionViewItem
import com.epam.mobitru.views.listview.UserViewItem
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ReviewOrderViewModel(
    private val orderRepository: OrderRepository,
) : BaseViewModel() {

    val list: Flow<List<Group>> = orderRepository.getDraftOrder
        .filter {
            if (!it.user.isAllFieldsFilled) {
                navigate(NavCommand.Direction(ReviewOrderFragmentDirections.actionReviewOrderFragmentToEditProfileFragment()))
            }
            it.user.isAllFieldsFilled
        }
        .map { order ->
            order.products.map {
                OrderViewItem(it.product, it.quantity)
            } + SectionViewItem(R.string.contact_details) +
                    UserViewItem(order.user) + SectionViewItem(R.string.payment_details) + PaymentDetailsViewItem(
                order.paymentDetails
            )
        }

    fun confirmOrder() {
        viewModelScope.launch {
            orderRepository.completeDraftOrder()
        }
    }
}