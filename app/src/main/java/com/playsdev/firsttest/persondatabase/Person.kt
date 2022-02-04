package com.playsdev.firsttest.persondatabase

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey val name:String,
    val surname:String,
    val date:String,
    val image:Bitmap
)