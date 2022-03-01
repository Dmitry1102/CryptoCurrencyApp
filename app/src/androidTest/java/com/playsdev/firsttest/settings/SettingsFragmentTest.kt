package com.playsdev.firsttest.settings

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.playsdev.firsttest.R
import com.playsdev.testapp.settings.SettingsFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest: TestCase(){

    private lateinit var initializeScenario: FragmentScenario<SettingsFragment>

    @Before
    public override fun setUp() {
        initializeScenario = launchFragmentInContainer(themeResId = R.style.Theme_FirstTest)
        initializeScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testFragment(){
        val name = "Adrian"
        val surname = "Nowak"

        onView(withId(R.id.edit_text_name)).perform(typeText(name))
        onView(withId(R.id.edit_surname)).perform(typeText(surname))
    }

}