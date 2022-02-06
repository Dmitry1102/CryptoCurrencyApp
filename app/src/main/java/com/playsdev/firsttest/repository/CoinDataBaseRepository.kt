package com.playsdev.firsttest.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.playsdev.firsttest.cloud.CoinApi
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.coindatabase.CoinDao
import com.playsdev.firsttest.coindatabase.CoinDataBase
import com.playsdev.firsttest.mediator.CoinMediator
import kotlinx.coroutines.flow.Flow

class CoinDataBaseRepository(
    private val coinDao:CoinDao,
    private val coinApi: CoinApi,
    private val coinDataBase: CoinDataBase
) {

    suspend fun addToDataBase(coin: PagingData<Coin>){
        coinDao.addToDataBase(coin)
    }

    @ExperimentalPagingApi
    fun getCoinFromDataBase(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Coin>> {

        val pagingSourceFactory = { coinDao.getCoinList() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CoinMediator(coinApi, coinDataBase)
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = CoinApi.DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

}