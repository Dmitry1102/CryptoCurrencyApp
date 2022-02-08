package com.playsdev.firsttest.adapter

import com.playsdev.firsttest.data.Coin

interface ItemClickListener {

    fun onClick(coin: Coin)
}