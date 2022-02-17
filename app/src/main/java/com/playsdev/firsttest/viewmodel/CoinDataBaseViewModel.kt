package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.repository.CoinDataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CoinDataBaseViewModel(
    private val coinDataBaseRepository: CoinDataBaseRepository
) : ViewModel() {

    fun addToDataBase(coin: List<Coin>) {
        viewModelScope.launch(Dispatchers.IO) {
            coinDataBaseRepository.addToDataBase(coin)
        }
    }

    @ExperimentalPagingApi
    fun getCoinList(): Flow<PagingData<Coin>> {
        return coinDataBaseRepository.getCoinFromDataBase().cachedIn(viewModelScope)
    }


}