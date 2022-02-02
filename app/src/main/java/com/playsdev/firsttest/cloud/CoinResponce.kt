package com.playsdev.firsttest.cloud

import com.google.gson.annotations.SerializedName
import java.util.ArrayList



data class CoinResponce(
     val current_price: Long,
     val id: String,
     val image: String,
     val symbol: String,
)

