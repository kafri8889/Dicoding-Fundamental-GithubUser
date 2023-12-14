package com.anafthdev.githubuser

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anafthdev.githubuser.extension.launchFragmentInHiltContainer
import com.anafthdev.githubuser.extension.waitUntilDisplayed
import com.anafthdev.githubuser.foundation.adapter.UserRecyclerViewAdapter
import com.anafthdev.githubuser.ui.dashboard.DashboardFragment
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchUser() {
        launchFragmentInHiltContainer<DashboardFragment>()

        onView(withId(R.id.searchBar))
            .perform(click())

        // Perform mengetik ke search view
        onView(allOf(isDescendantOfA(withId(R.id.searchView)), isAssignableFrom(EditText::class.java)))
            .perform(ViewActions.typeText("kafri8889"))
            .perform(ViewActions.pressImeActionButton())

        // Cek apakah search bar mempunyai text "kafri8889"
        onView(allOf(withText("kafri8889"), instanceOf(MaterialTextView::class.java)))
            .check(matches(isDisplayed()))

        // Tunggu sampai recycler view ditampilkan,
        // waktu delay yang diberikan tergantung kondisi koneksi
        onView(withId(R.id.recyclerViewUser))
            .perform(waitUntilDisplayed(delay = 60_000))

        // Klik item di recycler view
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserRecyclerViewAdapter.UserViewHolder>(0, click()))
    }

}