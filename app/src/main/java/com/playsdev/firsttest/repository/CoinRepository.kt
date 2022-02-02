package com.playsdev.firsttest.repository

import com.playsdev.firsttest.cloud.CoinApi

class CoinRepository(
    private val coinApi: CoinApi
) {
    suspend fun getCoinInfo() = coinApi.getCoinInfo()
}