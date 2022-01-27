package com.playsdev.firsttest.persondatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPerson(personEntity: PersonEntity)

    @Query("SELECT * FROM person_table LIMIT 1")
    fun setPerson():Flow<PersonEntity>

}