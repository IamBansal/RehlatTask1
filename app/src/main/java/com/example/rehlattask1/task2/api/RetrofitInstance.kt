package com.example.rehlattask1.task2.api

import com.example.rehlattask1.task2.utils.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RetrofitInstance {

    internal class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val newRequest: Request = request.newBuilder()
                .header("Content-Type", "application/json").build()
            return chain.proceed(newRequest)
        }
    }

    companion object {

        private val retrofit by lazy {
            val logging = LoggingInterceptor()
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val api: Api by lazy {
            retrofit.create(Api::class.java)
        }

    }
}