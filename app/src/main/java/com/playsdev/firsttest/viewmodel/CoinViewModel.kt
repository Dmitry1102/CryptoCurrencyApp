package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.cloud.ListResponce
import com.playsdev.firsttest.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<ListResponce>(ListResponce(arrayListOf()))
    val stateFlow: StateFlow<ListResponce> = _stateFlow.asStateFlow()

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val responce = coinRepository.getCoinInfo()
            _stateFlow.value = responce
        }

    }


}