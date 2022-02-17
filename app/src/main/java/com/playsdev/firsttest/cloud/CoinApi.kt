package com.playsdev.firsttest.cloud

import com.playsdev.firsttest.cloud.CoinService.DEFAULT_PAGE_SIZE
import com.playsdev.firsttest.data.AdditionalData
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.data.ChartData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {

    @GET("api/v3/coins/markets?vs_currency=usd")
    suspend fun getCoinPageToAdapter(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = DEFAULT_PAGE_SIZE,
    ): List<Coin>

    @GET("api/v3/coins/markets?vs_currency=usd&sparkline=false")
    suspend fun getAdditionalInfo(): List<AdditionalData>


    @GET("api/v3/coins/{id}/market_chart?vs_currency=usd&days=30&interval=daily")
    suspend fun getIndexCap(
        @Path("id") name: String,
        @Query("days") days: String,
        @Query("interval") interval: String
    ): ChartData

    @GET("api/v3/coins/markets?vs_currency=usd")
    suspend fun getCoinList(): List<Coin>


}




