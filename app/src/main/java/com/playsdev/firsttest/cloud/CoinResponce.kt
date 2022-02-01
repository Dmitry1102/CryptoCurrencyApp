package com.playsdev.firsttest.cloud

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ListResponce(
    @SerializedName("items") val items: ArrayList<CoinResponce>
)

data class CoinResponce(
    @SerializedName("current_price") val current_price: Int,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("symbol") val symbol: String,
)

