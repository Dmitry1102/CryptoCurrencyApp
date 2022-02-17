package com.playsdev.firsttest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.playsdev.firsttest.data.AdditionalData
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.data.ChartData
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    fun getCoinToAdapter(): Flow<PagingData<Coin>> {
        return coinRepository.getCoinToAdapter()
    }

    fun getAlphabeticToAdapter(): Flow<PagingData<Coin>> {
        return coinRepository.getAlphabeticToAdapter()
    }

    fun getPriceToAdapter(): Flow<PagingData<Coin>> {
        return coinRepository.getPriceToAdapter()
    }

    private val chartData = MutableStateFlow(ChartData(listOf(), listOf(), listOf()))
    private val additionalData:MutableStateFlow<List<AdditionalData>> =
        MutableStateFlow(listOf())

    fun getAdditionalInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            additionalData.value = coinRepository.getAdditionalInfo()
            Log.d("AKL","${additionalData.value}")
        }
    }

    fun setAdditionalData(): MutableStateFlow<List<AdditionalData>> {
        return additionalData
    }

    fun setChartData(name: String, days: String, interval: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chartData.value = coinRepository.getIndexCap(name, days, interval)
        }
    }

    fun getChartData(): MutableStateFlow<ChartData> {
        return chartData
    }

    suspend fun getCoin(): List<Coin> {
        return coinRepository.getCoinList()
    }


}