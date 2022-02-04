package com.playsdev.firsttest.repository

import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.coindatabase.CoinDao
import com.playsdev.firsttest.persondatabase.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinDataBaseRepository(
    private val coinDao:CoinDao
) {

    suspend fun addToDataBase(coin: List<Coin>){
        coinDao.addToDataBase(coin)
    }

    fun getCoinList(): Flow<List<CoinResponce>> = coinDao.getCoinList().map {
        it.map { coin ->
            CoinResponce(
                current_price = coin.current_price,
                id = coin.id,
                image = coin.image,
                symbol = coin.symbol
            )
        }
    }

}