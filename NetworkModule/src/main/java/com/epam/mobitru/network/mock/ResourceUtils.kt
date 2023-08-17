package com.epam.mobitru.network.mock

import okio.IOException
import okio.buffer
import okio.source
import timber.log.Timber
import java.nio.charset.StandardCharsets

object ResourceUtils {
    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    fun getJsonString(path: String): String {
        // Load the JSON response
        return try {
            this.javaClass
                .classLoader
                ?.getResourceAsStream(path)
                ?.source()
                ?.buffer()
                ?.readString(StandardCharsets.UTF_8)
                .orEmpty()
        } catch (e: IOException) {
            Timber.e(e,"Failed parse json at $path")
            ""
        }
    }
}