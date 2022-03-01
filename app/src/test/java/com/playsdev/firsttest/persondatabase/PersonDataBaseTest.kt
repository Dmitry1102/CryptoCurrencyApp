package com.playsdev.firsttest.persondatabase

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PersonDataBaseTest(): TestCase(){

    private lateinit var dataBase: PersonDataBase
    private lateinit var dao: PersonDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        .copy(Bitmap.Config.ARGB_8888, true)
    private val person = Person("Alex", "Bert", "26.01.2000", bitmap)

    @Before
    public override fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(context, PersonDataBase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        dao = dataBase.personDao()
    }


    @After
    fun closeDb() {
        dataBase.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun writeAndReadSpend() = runBlockingTest {
        val insertPerson = dao.insertPerson(person)

        val latch = CountDownLatch(1)
        val job = launch(Dispatchers.IO){
            dao.setPerson().collect {
                assertEquals(it,person)
                latch.countDown()
            }
        }

        latch.await()
        job.cancel()



    }

}