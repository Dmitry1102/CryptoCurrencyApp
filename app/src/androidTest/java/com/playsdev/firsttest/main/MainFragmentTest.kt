package com.playsdev.firsttest.main

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.playsdev.firsttest.R
import com.playsdev.firsttest.adapter.CoinScheduleViewHolder
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.repository.CoinDataBaseRepository
import com.playsdev.testapp.main.MainFragment
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class MainFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<MainFragment>


    @Before
    fun set() {
        scenario = launchFragmentInContainer<MainFragment>()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testFragment() {

        onView(withId(R.id.rv_currency)).perform()

    }
}