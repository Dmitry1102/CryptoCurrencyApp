package com.playsdev.firsttest.cloud

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinService {

    const val DEFAULT_PAGE_SIZE = 20
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