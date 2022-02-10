package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    fun getCoinToAdapter(): Flow<PagingData<Coin>>{
        return coinRepository.getCoinToAdapter()
    }

    fun getAlphabeticToAdapter(): Flow<PagingData<Coin>>{
        return coinRepository.getAlphabeticToAdapter()
    }

    fun getPriceToAdapter():Flow<PagingData<Coin>>{
        return coinRepository.getPriceToAdapter()
    }


    suspend fun getCoin(): List<Coin> {
        return coinRepository.getCoinList()
    }



}