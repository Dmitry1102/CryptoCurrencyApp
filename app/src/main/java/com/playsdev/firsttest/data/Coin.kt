package com.playsdev.firsttest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "coin_table")
@Parcelize
data class Coin (
    @PrimaryKey val current_price: Float,
    val id: String,
    val image: String,
    val symbol: String,
):Parcelable