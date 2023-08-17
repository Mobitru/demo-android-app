package com.epam.mobitru.network.mock

import com.epam.mobitru.network.ENABLE_SSL_PINNING
import com.epam.mobitru.network.LocalHostSocketFactory
import okhttp3.mockwebserver.MockWebServer
import timber.log.Timber

class MockServerManager {
    var port = 1
    var scheme: String? = null
        get() {
            return when {
                field != null ->
                    field
                !ENABLE_SSL_PINNING ->
                    HTTP_SCHEME
                else ->
                    HTTPS_SCHEME
            }
        }
    private val mockApis = mutableMapOf<String, NetworkScenarios>()
    private var mockServer: MockWebServer? = null
    private var isServerStarted = false

    fun startServer() {
        if (!isServerStarted) {
            Thread {
                mockServer = MockWebServer().apply {
                    dispatcher = ApiDispatcher(mockApis)
                    start()
                    this@MockServerManager.port = port
                    Timber.d("Started mock server at: ${url("")}")
                }
                isServerStarted = true
            }.start()
        }
    }

    fun startSslServer(keyStoreFile: String? = LocalHostSocketFactory.DEFAULT_KEYSTORE) {
        if (!isServerStarted) {
            Thread {
                mockServer = MockWebServer().apply {
                    useHttps(LocalHostSocketFactory.getSocketFactory(keyStoreFile), false)
                    dispatcher = ApiDispatcher(mockApis)
                    start()
                    this@MockServerManager.port = port
                    Timber.d("Started mock ssl server at: ${url("")}")
                }
                isServerStarted = true
            }.start()
        }
    }

    fun stopServer() {
        isServerStarted = false
        mockServer?.shutdown()
    }

    fun enableApi(api: String, scenarios: NetworkScenarios) {
        mockApis[api] = scenarios
    }

    fun disableApi(api: String) {
        if (mockApis.contains(api)) {
            mockApis.remove(api)
        }
    }

    fun shouldMockApi(api: String): Boolean {
        mockApis.forEach {
            if (api.contains(it.key)) {
                return true
            }
        }
        return false
    }

    companion object {
        const val HTTP_SCHEME = "http"
        const val HTTPS_SCHEME = "https"
        const val HOST = "localhost"
    }
}