package com.playsdev.firsttest.repository

import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.coindatabase.CoinDao
import com.playsdev.firsttest.persondatabase.Person
import kotlinx.coroutines.flow.Flow

class CoinDataBaseRepository(
    private val coinDao:CoinDao
) {

    suspend fun addToDataBase(coin: List<Coin>){
        coinDao.addToDataBase(coin)
    }

    fun getCoinList(): Flow<List<Coin>> = coinDao.getCoinList()

}