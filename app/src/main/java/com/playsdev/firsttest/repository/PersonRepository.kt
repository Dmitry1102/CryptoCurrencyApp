package com.playsdev.firsttest.repository

import com.playsdev.firsttest.persondatabase.Person
import com.playsdev.firsttest.persondatabase.PersonDao
import kotlinx.coroutines.flow.Flow


class PersonRepository(
     private val personDao: PersonDao
) {
     fun setPerson(): Flow<List<Person>> = personDao.setPerson()

     suspend fun addPersonToDataBase(person: Person){
          personDao.insertPerson(person)
     }


}

