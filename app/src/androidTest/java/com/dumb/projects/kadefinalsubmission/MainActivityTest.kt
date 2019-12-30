package com.dumb.projects.kadefinalsubmission

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSearchMatch() {
//        MainActivity
        onView(withId(R.id.toolbar_search_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar_search_menu)).perform(click())
//        SearchMatchActivity
        onView(withId(R.id.floating_search_view)).check(matches(isDisplayed()))
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Arsenal"))
        onView(withId(R.id.progress_loading)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(isAssignableFrom(EditText::class.java)).perform(clearText(), typeText("Chelsea"))
        onView(withId(R.id.progress_loading)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        Thread.sleep(10000)
    }
}