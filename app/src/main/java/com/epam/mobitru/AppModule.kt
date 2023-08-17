package com.epam.mobitru

import com.epam.mobitru.main.MainFragment
import com.epam.mobitru.main.MainViewModel
import com.epam.mobitru.mappers.mapperModule
import com.epam.mobitru.network.networkModule
import com.epam.mobitru.repository.CartRepository
import com.epam.mobitru.repository.OrderRepository
import com.epam.mobitru.repository.ProductsRepository
import com.epam.mobitru.repository.UserRepository
import com.epam.mobitru.screens.account.AccountFragment
import com.epam.mobitru.screens.account.AccountViewModel
import com.epam.mobitru.screens.cart.CartFragment
import com.epam.mobitru.screens.cart.CartViewModel
import com.epam.mobitru.screens.completedOrder.CompletedOrderFragment
import com.epam.mobitru.screens.completedOrder.CompletedOrderViewModel
import com.epam.mobitru.screens.editProfile.EditProfileFragment
import com.epam.mobitru.screens.editProfile.EditProfileViewModel
import com.epam.mobitru.screens.home.HomeFragment
import com.epam.mobitru.screens.home.HomeViewModel
import com.epam.mobitru.screens.home.sort.SortFragment
import com.epam.mobitru.screens.home.sort.SortViewModel
import com.epam.mobitru.screens.login.LoginFragment
import com.epam.mobitru.screens.login.LoginViewModel
import com.epam.mobitru.screens.orders.OrdersFragment
import com.epam.mobitru.screens.orders.OrdersViewModel
import com.epam.mobitru.screens.reviewOrder.ReviewOrderFragment
import com.epam.mobitru.screens.reviewOrder.ReviewOrderViewModel
import com.epam.mobitru.screens.showOrder.ShowOrderFragment
import com.epam.mobitru.screens.showOrder.ShowOrderViewModel
import org.koin.androidx.fragment.dsl.fragmentOf
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::UserRepository)
    singleOf(::ProductsRepository)
    singleOf(::CartRepository)
    singleOf(::OrderRepository)


    viewModelOf(::MainViewModel)
    fragmentOf(::MainFragment)

    viewModelOf(::LoginViewModel)
    fragmentOf(::LoginFragment)

    viewModelOf(::AccountViewModel)
    fragmentOf(::AccountFragment)

    viewModelOf(::HomeViewModel)
    fragmentOf(::HomeFragment)

    viewModelOf(::CartViewModel)
    fragmentOf(::CartFragment)

    viewModelOf(::OrdersViewModel)
    fragmentOf(::OrdersFragment)

    viewModelOf(::SortViewModel)
    fragmentOf(::SortFragment)

    viewModelOf(::ReviewOrderViewModel)
    fragmentOf(::ReviewOrderFragment)

    viewModelOf(::EditProfileViewModel)
    fragmentOf(::EditProfileFragment)

    viewModelOf(::ShowOrderViewModel)
    fragmentOf(::ShowOrderFragment)

    viewModelOf(::CompletedOrderViewModel)
    fragmentOf(::CompletedOrderFragment)

} + networkModule + mapperModule