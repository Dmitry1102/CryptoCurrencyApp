package com.playsdev.firsttest.repository

import com.playsdev.firsttest.persondatabase.PersonDao
import com.playsdev.firsttest.persondatabase.PersonEntity

class PersonRepository(
    private val personDao: PersonDao
) {

    suspend fun addPerson(peron: PersonEntity) {
        personDao.addPerson(peron)
    }

    suspend fun loadName():String{
        return personDao.loadName()
    }

}