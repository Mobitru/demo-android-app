package com.epam.mobitru.network.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class OrdersApiDispatcher(
    private val mockApis: Map<String, NetworkScenarios>
) : Dispatcher() {

    companion object {
        const val ORDERS_URL = "/orders"
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (mockApis[ORDERS_URL]) {
            NetworkScenarios.SUCCESS ->
                MockUtils.success("OrdersResponse.json")
            NetworkScenarios.FAILURE ->
                MockUtils.failure(500)
            else ->
                MockUtils.default
        }
    }
}