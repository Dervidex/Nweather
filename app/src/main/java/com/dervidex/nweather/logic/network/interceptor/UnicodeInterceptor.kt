package com.dervidex.nweather.logic.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UnicodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}