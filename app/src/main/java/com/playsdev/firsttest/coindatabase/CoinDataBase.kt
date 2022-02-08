package com.playsdev.firsttest.coindatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.data.CoinKey

@Database(entities = [Coin::class, CoinKey::class], version = 1, exportSchema = false )
abstract class CoinDataBase: RoomDatabase() {
    abstract fun coinDao():CoinDao
    abstract fun coinKeyDao(): CoinKeysDao

}


object CoinDatabaseConstructor {

    fun create(context: Context): CoinDataBase =
        Room.databaseBuilder(
            context,
            CoinDataBase::class.java,
            "app_table"
        ).fallbackToDestructiveMigration().build()
}