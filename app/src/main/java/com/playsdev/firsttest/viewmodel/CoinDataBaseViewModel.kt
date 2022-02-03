package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.persondatabase.Person
import com.playsdev.firsttest.repository.CoinDataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CoinDataBaseViewModel(
    private val coinDataBaseRepository: CoinDataBaseRepository
): ViewModel() {

    fun addToDataBase(coin: List<Coin>){
        viewModelScope.launch(Dispatchers.IO) {
            coinDataBaseRepository.addToDataBase(coin)
        }
    }

    fun getCoiList(): Flow<List<Coin>> = coinDataBaseRepository.getCoinList()

}