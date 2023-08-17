package com.epam.mobitru.network

import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

object LocalHostSocketFactory {
    const val DEFAULT_KEYSTORE = "localhost.keystore.jks"

    fun getSocketFactory(keystore: String?): SSLSocketFactory {
        val stream = this.javaClass.classLoader?.getResourceAsStream(keystore)
        val serverKeyStorePassword = "123456".toCharArray()
        val serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        serverKeyStore.load(stream, serverKeyStorePassword)

        val kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm()
        val kmf = KeyManagerFactory.getInstance(kmfAlgorithm)
        kmf.init(serverKeyStore, serverKeyStorePassword)

        val trustManagerFactory = TrustManagerFactory.getInstance(kmfAlgorithm)
        trustManagerFactory.init(serverKeyStore)

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(kmf.keyManagers, trustManagerFactory.trustManagers, null)
        return sslContext.socketFactory
    }
}