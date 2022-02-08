package com.playsdev.firsttest.coindatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.playsdev.firsttest.data.CoinKey

@Dao
interface CoinKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CoinKey>)

    @Query("SELECT * FROM coin_keys WHERE id = :id")
    suspend fun remoteCoinKeys(id: String): CoinKey?

    @Query("DELETE FROM coin_keys")
    suspend fun clearCoinKeys()
}