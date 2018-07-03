package com.evgeniy.restapp

import com.vk.sdk.VKAccessToken
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor(private val apiV: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original = chain?.request()
        val originalHttpUrl = original?.url()

        val url = originalHttpUrl?.newBuilder()
                ?.addQueryParameter("access_token", VKAccessToken.currentToken().accessToken)
                ?.addQueryParameter("v", apiV)
                ?.build()

        val requestBuilder = original?.newBuilder()
                ?.url(url)

        val request = requestBuilder?.build()
        return chain?.proceed(request)
    }
}