package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.persondatabase.PersonEntity
import com.playsdev.firsttest.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonDataViewModel(
    private val personRepository: PersonRepository
): ViewModel() {

    private var _stateFlow = MutableStateFlow<String>("String")
    private var stateFlow: StateFlow<String> = _stateFlow.asStateFlow()
    get() = _stateFlow

    fun addPersonToDatabase(name:String,surname:String,date:String,image: String) {
        val newPerson = PersonEntity(
            name = name,
            surname = surname,
            date = date,
            image = image
        )
        viewModelScope.launch(Dispatchers.IO) {
            personRepository.addPerson(newPerson)
        }
    }

   fun loadName(){
        viewModelScope.launch(Dispatchers.IO) {
            val loadName = personRepository.loadName()
            _stateFlow.value = loadName
        }
    }
}