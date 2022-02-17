package com.playsdev.firsttest.data

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdditionalData(
    @PrimaryKey val id: String,
    val high_24h: Double,
    val low_24h: Double,
    val market_cap: Long,
    val price_change_percentage_24h: Double
):Parcelable