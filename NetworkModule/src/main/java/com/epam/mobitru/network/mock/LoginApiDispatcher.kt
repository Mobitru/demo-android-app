package com.epam.mobitru.network.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import timber.log.Timber

class LoginApiDispatcher(
    private val mockApis: Map<String, NetworkScenarios>
) : Dispatcher() {

    companion object {
        const val LOGIN_URL = "/login"
        const val LOGIN_BIO_URL = "/loginBio"
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (request.requestUrl?.encodedPath == LOGIN_BIO_URL) {
            return MockUtils.success("LoginBioResponse.json")
        }
        val requestString = request.body.readUtf8()
        val expected = ResourceUtils.getJsonString("json/LoginRequest.json")
        return try {
            JSONAssert.assertEquals(expected, requestString, JSONCompareMode.LENIENT)
            MockUtils.success("LoginResponse.json")
        } catch (e: AssertionError) {
            Timber.w(e)
            MockUtils.failure(401)
        }
    }
}