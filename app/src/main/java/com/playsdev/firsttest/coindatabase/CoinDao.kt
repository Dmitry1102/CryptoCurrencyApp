package com.playsdev.firsttest.coindatabase

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDataBase(coin: List<Coin>)

    @Query("SELECT * FROM coin_table")
    fun getCoinListToAdapter(): PagingSource<Int, Coin>

    @Query("DELETE FROM coin_table")
    suspend fun clearInfo()


}