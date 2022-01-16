package com.diary.someday.model.network.util

import com.diary.someday.model.network.util.PreferenceUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("access_token", token?:"").build()
        return chain.proceed(request)
    }
}