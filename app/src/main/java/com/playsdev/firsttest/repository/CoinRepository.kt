package com.playsdev.firsttest.repository

import com.playsdev.firsttest.cloud.CoinApi
import com.playsdev.firsttest.cloud.ListResponce

class CoinRepository(
    private val coinApi: CoinApi
) {

    suspend fun getCoinInfo():ListResponce{
        return coinApi.getCoinInfo()
    }




}