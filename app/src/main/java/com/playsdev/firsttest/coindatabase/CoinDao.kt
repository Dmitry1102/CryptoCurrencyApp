package com.playsdev.firsttest.coindatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.persondatabase.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDataBase(coin: List<Coin>)

    @Query("SELECT * FROM coin_table")
    fun getCoinList(): Flow<List<Coin>>


}