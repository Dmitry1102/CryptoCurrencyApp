package com.playsdev.firsttest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_table")
data class Coin (
    @PrimaryKey val current_price: Float,
    val id: String,
    val image: String,
    val symbol: String,
)