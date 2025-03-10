package com.hostelworld.qacodechallenge

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    var firstName = "John"
    var lastName = "Doe"
    var testEmail = "test@email.com"
    var password = "Admin123"

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @BeforeClass
    fun setUp() {
        onView(withId(R.id.firstNameEt)).perform(typeText(firstName))
        onView(withId(R.id.lastNameEt)).perform(typeText(lastName))
        onView(withId(R.id.emailEt)).perform(typeText(testEmail))
        onView(withId(R.id.passwordEt)).perform(typeText(password))
        onView(withId(R.id.btnCreateAccount)).perform(click())
    }

    @Test
    fun newlyRegisteredValidUserIsAbleToLoginTest() {
        onView(withId(R.id.btnLogin)).perform(typeText(testEmail))
        onView(withId(R.id.usernameEt)).perform(typeText(password))
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.greetingTv)).check(matches(withText("User Logged In")))
        onView(withId(R.id.firstNameTv)).check(matches(withText("First Name: " + firstName)))
        onView(withId(R.id.lastNameTv)).check(matches(withText("Last Name: " + lastName)))
        onView(withId(R.id.emailTv)).check(matches(withText("Email: " + testEmail)))
    }

    @Test
    fun loginWithNonExistingUser() {
        onView(withId(R.id.btnLogin)).perform(typeText("user"))
        onView(withId(R.id.usernameEt)).perform(typeText("password"))
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withText("Invalid email")).check(matches(isDisplayed()))
    }

    @Test
    fun loginWithWrongPassword() {
        onView(withId(R.id.btnLogin)).perform(typeText(testEmail))
        onView(withId(R.id.usernameEt)).perform(typeText("wrongPassword"))
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withText("Invalid password")).check(matches(isDisplayed()))
    }

    @Test
    fun nonLoggedInUserCantAccessUserDetailsPage() {
        onView(withId(R.id.greetingTv)).perform(click())
        onView(withText("Create Account")).check(matches(isDisplayed()))
    }

}
