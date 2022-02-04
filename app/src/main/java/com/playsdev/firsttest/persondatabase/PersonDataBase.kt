package com.playsdev.firsttest.persondatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.playsdev.firsttest.settings.Converters

@Database(entities = [Person::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PersonDataBase: RoomDatabase() {
    abstract fun personDao():PersonDao
}

object PersonDatabaseConstructor {
    fun create(context: Context): PersonDataBase =
        Room.databaseBuilder(
            context,
            PersonDataBase::class.java,
            "person_table"
        ).fallbackToDestructiveMigration().build()
}