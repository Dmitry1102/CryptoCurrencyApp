package com.playsdev.firsttest.cloud

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CoinApi {

    @GET("/coins/markets/")
    suspend fun getCoinInfo(): ListResponce


    companion object {
        private const val URL = "https://api.coingecko.com/api/v3/"

        fun apiService(): CoinApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .baseUrl(URL)
                .build()

            return retrofit.create(CoinApi::class.java)
        }
    }
}