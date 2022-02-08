package com.playsdev.firsttest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_keys")
data class CoinKey(
    @PrimaryKey val id:String,
    val prevKey: Int?,
    val nextKey: Int?
)