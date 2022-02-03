package com.playsdev.firsttest.persondatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun setPerson(): Flow<List<Person>>

}