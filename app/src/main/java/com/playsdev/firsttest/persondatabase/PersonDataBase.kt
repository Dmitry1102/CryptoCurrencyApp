package com.playsdev.firsttest.persondatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonEntity::class], version = 1)
abstract class PersonDataBase: RoomDatabase() {
    abstract fun personDao():PersonDao
}

object DatabaseConstructor {
    fun create(context: Context): PersonDataBase =
        Room.databaseBuilder(
            context,
            PersonDataBase::class.java,
            "person_database"
        ).build()
}