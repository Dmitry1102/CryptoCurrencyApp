package com.playsdev.firsttest.repository

import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.coindatabase.CoinDao
import com.playsdev.firsttest.persondatabase.Person

class CoinDataBaseRepository(
    private val coinDao:CoinDao
) {

    suspend fun addToDataBase(coin: Coin){
        coinDao.addToDataBase(coin)
    }

}