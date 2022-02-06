package com.playsdev.firsttest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.playsdev.firsttest.cloud.CoinApi
import com.playsdev.firsttest.cloud.CoinApi.Companion.DEFAULT_PAGE_SIZE
import com.playsdev.firsttest.cloud.CoinPagingSource
import com.playsdev.firsttest.coindatabase.Coin
import kotlinx.coroutines.flow.Flow

class CoinRepository(
    private val coinApi: CoinApi
) {

    fun getCoinInfo(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<Coin>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {CoinPagingSource(coinApi)}
        ).flow
    }



    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}