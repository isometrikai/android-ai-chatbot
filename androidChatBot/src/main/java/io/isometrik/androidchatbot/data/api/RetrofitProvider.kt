package io.isometrik.androidchatbot.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Logs request and response body
        return logging
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://service-apis.isometrik.io/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val guestAuthService: ChatBotService by lazy {
        retrofit.create(ChatBotService::class.java)
    }
}
