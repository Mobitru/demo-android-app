package com.epam.mobitru.network.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ProductsApiDispatcher(
    private val mockApis: Map<String, NetworkScenarios>
) : Dispatcher() {

    companion object {
        const val PRODUCTS_URL = "/products"
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (mockApis[PRODUCTS_URL]) {
            NetworkScenarios.SUCCESS ->
                MockUtils.success("ProductsResponse.json")
            NetworkScenarios.FAILURE ->
                MockUtils.failure(500)
            else ->
                MockUtils.default
        }
    }
}