package com.playsdev.firsttest.coindatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.playsdev.firsttest.cloud.CoinResponce

@Database(entities = [Coin::class], version = 1, exportSchema = false )
abstract class CoinDataBase: RoomDatabase() {
    abstract fun coinDao():CoinDao
}

object CoinDatabaseConstructor {
    fun create(context: Context): CoinDataBase =
        Room.databaseBuilder(
            context,
            CoinDataBase::class.java,
            "coin_table"
        ).fallbackToDestructiveMigration().build()
}