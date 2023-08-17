package com.epam.mobitru.network

import com.epam.mobitru.network.models.LoginRequest
import com.epam.mobitru.network.models.LoginResponse
import com.epam.mobitru.network.models.OrderItem
import com.epam.mobitru.network.models.ProductItem
import retrofit2.http.*


interface RetrofitApi {

    @POST("login")
    suspend fun login(@Body user: LoginRequest): LoginResponse

    @POST("loginBio")
    suspend fun loginBio(): LoginResponse

    @GET("products")
    suspend fun getProductsList(): List<ProductItem>

    @GET("orders")
    suspend fun getOrdersList(): List<OrderItem>
}