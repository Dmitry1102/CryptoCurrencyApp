package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    fun getCoinToAdapter(): Flow<PagingData<Coin>>{
        return coinRepository.getCoinToAdapter()
    }


    suspend fun getCoin(): List<Coin> {
        return coinRepository.getCoinList()
    }



}