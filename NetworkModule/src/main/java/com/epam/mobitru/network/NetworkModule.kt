package com.epam.mobitru.network

import com.epam.mobitru.network.mock.DebugUrlInterceptor
import com.epam.mobitru.network.mock.MockServerManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

const val BASE_URL = "http://localhost:8080/"

val networkModule = module {
    single<RetrofitApi> {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(RetrofitApi::class.java)
    }

    factory<HttpLoggingInterceptor> {
        HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }.apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single<DebugUrlInterceptor> {
        DebugUrlInterceptor().apply {
            setMockServerManger(get())
        }
    }

    single<MockServerManager> {
        MockServerManager()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<DebugUrlInterceptor>())
            .build()
    }
}