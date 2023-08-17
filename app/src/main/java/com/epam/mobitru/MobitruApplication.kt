package com.epam.mobitru

import android.app.Application
import com.epam.mobitru.network.mock.*
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext
import timber.log.Timber

class MobitruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        Timber.plant(Timber.DebugTree())
        GlobalContext.startKoin {
            androidLogger()
            fragmentFactory()
            androidContext(this@MobitruApplication)
            modules(appModule)
        }


        val sm: MockServerManager = get()
        sm.startServer()
        sm.enableApi(LoginApiDispatcher.LOGIN_URL, NetworkScenarios.SUCCESS)
        sm.enableApi(LoginApiDispatcher.LOGIN_BIO_URL, NetworkScenarios.SUCCESS)
        sm.enableApi(ProductsApiDispatcher.PRODUCTS_URL, NetworkScenarios.SUCCESS)
        sm.enableApi(OrdersApiDispatcher.ORDERS_URL, NetworkScenarios.SUCCESS)
    }
}