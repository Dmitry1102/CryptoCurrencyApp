package com.playsdev.firsttest.persondatabase

import android.content.Context
import androidx.room.*
import com.playsdev.firsttest.settings.Converters

@Database(entities = [PersonEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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