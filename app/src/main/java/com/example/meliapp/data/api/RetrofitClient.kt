/**
 * Singleton object for configuring and providing Retrofit client for API calls.
 * Sets up OkHttp client with Chucker interceptor for debug builds and Moshi converter for JSON parsing.
 * Layer: Data
 * Usage: Provides the MercadoLibreApi instance for network operations.
 */
package com.example.meliapp.data.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.meliapp.MeliApplication
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.mercadolibre.com/"

    private val moshi = Moshi.Builder().build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(MeliApplication().applicationContext))
        .build()

    val api: MercadoLibreApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(MercadoLibreApi::class.java)
}