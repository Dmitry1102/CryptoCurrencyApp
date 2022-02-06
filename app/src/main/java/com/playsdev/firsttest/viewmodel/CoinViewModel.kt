package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    fun getCoin(): Flow<PagingData<Coin>>{
        return coinRepository.getCoinInfo()
    }

}