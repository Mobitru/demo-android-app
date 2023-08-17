package com.epam.mobitru.screens.account

import androidx.lifecycle.viewModelScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseViewModel
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.navigate
import com.epam.mobitru.main.MainFragmentDirections
import com.epam.mobitru.repository.CartRepository
import com.epam.mobitru.repository.OrderRepository
import com.epam.mobitru.repository.ProductsRepository
import com.epam.mobitru.repository.UserRepository
import com.epam.mobitru.views.listview.ItemViewItem
import com.epam.mobitru.views.listview.SectionViewItem
import com.epam.mobitru.views.listview.UserViewItem
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    private val userItemFlow: Flow<Group> = userRepository.currentUser.map {
        UserViewItem(it) {
            navigate(NavCommand.Direction(MainFragmentDirections.actionMainFragmentToEditProfileFragment()))
        }
    }

    val list: Flow<List<Group>> = combine(userItemFlow) {
        listOf(
            it.first(),
            SectionViewItem(R.string.about_section_general),
            ItemViewItem(R.string.menu_about) {
                navigate(NavCommand.Direction(MainFragmentDirections.actionMainFragmentToAboutFragment()))
            }
        )
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            cartRepository.logout()
            orderRepository.logout()
            productsRepository.logout()
            navigate(NavCommand.Direction(MainFragmentDirections.actionMainFragmentToLoginFragment()))
        }
    }
}