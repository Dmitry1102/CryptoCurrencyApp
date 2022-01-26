package com.playsdev.firsttest.persondatabase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
class PersonEntity (
    @PrimaryKey val name:String,
    val surname:String,
    val date: String,
    val image: String
)

