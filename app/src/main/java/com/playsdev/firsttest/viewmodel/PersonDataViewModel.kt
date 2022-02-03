package com.playsdev.firsttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playsdev.firsttest.persondatabase.Person
import com.playsdev.firsttest.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class PersonDataViewModel(
    private val personRepository: PersonRepository
) : ViewModel() {

    fun setPerson(): Flow<List<Person>> = personRepository.setPerson()

    fun addPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            personRepository.addPersonToDataBase(person)
        }
    }


}