package com.playsdev.firsttest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.playsdev.firsttest.cloud.AlphabeticPagingSource
import com.playsdev.firsttest.cloud.CoinApi
import com.playsdev.firsttest.cloud.CoinPagingSource
import com.playsdev.firsttest.cloud.CoinService.DEFAULT_PAGE_SIZE
import com.playsdev.firsttest.cloud.PricePagingSource
import com.playsdev.firsttest.data.AdditionalData
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.data.ChartData
import kotlinx.coroutines.flow.Flow

class CoinRepository(
    private val coinApi: CoinApi
) {

    fun getCoinToAdapter(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<Coin>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CoinPagingSource(coinApi) }
        ).flow
    }

    fun getAlphabeticToAdapter(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<Coin>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { AlphabeticPagingSource(coinApi)}
        ).flow
    }

    fun getPriceToAdapter(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<Coin>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PricePagingSource(coinApi) }
        ).flow
    }

    suspend fun getAdditionalInfo(): List<AdditionalData> = coinApi.getAdditionalInfo()


    suspend fun getIndexCap(name:String, days:String, interval: String) = coinApi.getIndexCap(name,days,interval)

    suspend fun getCoinList(): List<Coin> = coinApi.getCoinList()

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}