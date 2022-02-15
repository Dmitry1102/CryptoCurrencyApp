package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.data.ChartData
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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



//    fun indexCap(change:String){
//        viewModelScope.launch(Dispatchers.IO) {
//          stateFlow = coinRepository.getIndexCap(change) as StateFlow<ChartData>
//        }
//    }


    suspend fun getCoin(): List<Coin> {
        return coinRepository.getCoinList()
    }



}