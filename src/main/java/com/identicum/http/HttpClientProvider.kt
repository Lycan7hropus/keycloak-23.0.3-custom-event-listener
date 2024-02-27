package com.identicum.http
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object HttpClientProvider {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(Constants.apiConnectTimeout, TimeUnit.SECONDS)
            .readTimeout(Constants.apiSocketTimeout, TimeUnit.SECONDS)
            .writeTimeout(Constants.apiSocketTimeout, TimeUnit.SECONDS)
            .build()
    }
}