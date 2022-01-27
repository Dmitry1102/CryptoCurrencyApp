package com.playsdev.firsttest.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.persondatabase.PersonEntity
import com.playsdev.firsttest.repository.Person
import com.playsdev.firsttest.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonDataViewModel(
    private val personRepository: PersonRepository
) : ViewModel() {

    private var _stateFlow = MutableStateFlow<String>("String")
    private var stateFlow: StateFlow<String> = _stateFlow.asStateFlow()
        get() = _stateFlow

    val setPerson: Flow<Person> = personRepository.setPerson()

    fun addPersonToDatabase(person: PersonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            personRepository.addPerson(person)
        }
    }


}