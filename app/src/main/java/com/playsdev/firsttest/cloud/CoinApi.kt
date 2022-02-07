package com.playsdev.firsttest.cloud

import com.playsdev.firsttest.cloud.CoinService.DEFAULT_PAGE_SIZE
import com.playsdev.firsttest.coindatabase.Coin
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {

    @GET("api/v3/coins/markets?vs_currency=usd")
    suspend fun getCoinPageToAdapter(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = DEFAULT_PAGE_SIZE
    ): List<Coin>

    @GET("api/v3/coins/markets?vs_currency=usd")
    suspend fun getCoinList():List<Coin>

}




