package com.playsdev.firsttest.cloud

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {

    @GET("api/v3/coins/markets?vs_currency=usd")
    suspend fun getCoinInfo(): List<CoinResponce>


    companion object {
        private const val URL = "https://api.coingecko.com/"

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




