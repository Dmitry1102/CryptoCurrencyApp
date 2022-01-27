package com.playsdev.firsttest.repository


import com.playsdev.firsttest.persondatabase.PersonDao
import com.playsdev.firsttest.persondatabase.PersonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PersonRepository(
    private val personDao: PersonDao
) {

    suspend fun addPerson(person: PersonEntity) {
        personDao.addPerson(person)
    }

    fun setPerson(): Flow<Person> = personDao.setPerson().map { personEntity ->
        Person(
            name = personEntity.name,
            surname = personEntity.surname,
            date = personEntity.date,
            image = personEntity.image
        )
    }

}

