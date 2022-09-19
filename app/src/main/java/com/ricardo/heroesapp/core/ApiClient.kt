package com.ricardo.heroesapp.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var serviceApiInterface: IServiceApiClient

    fun build(): IServiceApiClient {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())

        val httpClientBuilder = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor())
        }

        val retrofit = retrofitBuilder.client(httpClientBuilder.build()).build()

        this.serviceApiInterface = retrofit.create(IServiceApiClient::class.java)
        return this.serviceApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
}