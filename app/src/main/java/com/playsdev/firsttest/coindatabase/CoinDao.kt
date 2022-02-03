package com.playsdev.firsttest.coindatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.playsdev.firsttest.cloud.CoinResponce

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDataBase(coin: List<Coin>)
}