package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {


    private val _stateFlow = MutableStateFlow(listOf(CoinResponce(0.0F,"","","")))

    fun getInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val responce = coinRepository.getCoinInfo()
            _stateFlow.value = responce
        }
    }

   val stateFlow: StateFlow<List<CoinResponce>> = _stateFlow.asStateFlow()


}