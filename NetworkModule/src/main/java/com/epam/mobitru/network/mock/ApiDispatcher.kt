package com.epam.mobitru.network.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import kotlin.random.Random

class ApiDispatcher(
    private val mockApis: Map<String, NetworkScenarios>
) : Dispatcher() {

    companion object {
        private const val NETWORK_DELAY_MINIMUM = 100L
        private const val NETWORK_DELAY_MAXIMUM = 1000L
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val toSleep: Long =
            NETWORK_DELAY_MINIMUM + Random.nextLong(NETWORK_DELAY_MAXIMUM - NETWORK_DELAY_MINIMUM)
        Thread.sleep(toSleep)
        return when {
            request.path?.startsWith(LoginApiDispatcher.LOGIN_URL) == true ->
                LoginApiDispatcher(mockApis).dispatch(request)
            request.path?.startsWith(ProductsApiDispatcher.PRODUCTS_URL) == true ->
                ProductsApiDispatcher(mockApis).dispatch(request)
            request.path?.startsWith(OrdersApiDispatcher.ORDERS_URL) == true ->
                OrdersApiDispatcher(mockApis).dispatch(request)
            else ->
                MockResponse().setResponseCode(404)
        }
    }
}