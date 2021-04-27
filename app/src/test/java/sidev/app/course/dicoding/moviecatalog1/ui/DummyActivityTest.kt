package sidev.app.course.dicoding.moviecatalog1.ui

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.ui.activity.DummyAct
import sidev.app.course.dicoding.moviecatalog1.ui.activity.MainActivity
import sidev.lib.console.prine

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DummyActivityTest {

    @get:Rule
    val actRule = ActivityScenarioRule(DummyAct::class.java)

    //TODO: UI Testing

    @Test
    fun tvTest(){
        prine("Test mulai")
        onView(
            withId(R.id.tv)
        ).check(
            matches(
                withText("Halo bro!")
            )
        )
        prine("Test selesai")
    }
    @Test
    fun tvTest2(){
        prine("Test mulai")
        onView(
            withId(R.id.tv)
        ).check(
            matches(
                withText("Halo bro!")
            )
        )
        prine("Test selesai")
    }
}