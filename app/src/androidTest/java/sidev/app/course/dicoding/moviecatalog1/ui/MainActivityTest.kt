package sidev.app.course.dicoding.moviecatalog1.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.AndroidTestingUtil
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.repository.ShowEmptyRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowErrorRepo
import sidev.app.course.dicoding.moviecatalog1.ui.activity.MainActivity
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil

class MainActivityTest {

    @get:Rule
    val actRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        TestingUtil.isUiAsyncTest = true
        IdlingRegistry.getInstance().register(TestingUtil.idlingRes)
    }

    @After
    fun finish(){
        IdlingRegistry.getInstance().unregister(TestingUtil.idlingRes)
        TestingUtil.resetDefautlShowRepo()
    }

    @Test
    fun getShowList(){
        onView(withId(R.id.rv)).apply {
            // Assert RecyclerView is displayed and not empty
            check(
                AndroidTestingUtil.RecyclerViewAssertion.isChildInPositionDisplayed(
                    0, ViewMatchers.isDisplayed()
                )
            )
            // Assert first item title is displayed and not template
            val strTitle = ApplicationProvider.getApplicationContext<Context>().getString(R.string.title)
            check(
                AndroidTestingUtil.RecyclerViewAssertion.isChildIdInPositionDisplayed(
                    0, R.id.tv_title,
                    AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                        it.isNotBlank() && it != strTitle
                    }
                )
            )
            // Assert first item release date is displayed and not template
            val relDatTitle = ApplicationProvider.getApplicationContext<Context>().getString(R.string.release_date)
            check(
                AndroidTestingUtil.RecyclerViewAssertion.isChildIdInPositionDisplayed(
                    0, R.id.tv_release,
                    AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                        it.isNotBlank() && it != relDatTitle
                    }
                )
            )
        }
        // Assert loading progress bar should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView should be gone.
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
    }

    @Test
    fun getShowListOnError(){
        TestingUtil.defaultShowRepo = ShowErrorRepo
        // Assert RecyclerView should be gone.
        onView(withId(R.id.rv)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert loading progress should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView is displayed with text starts with 'Error:' and contains 'cause:'.
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatches {
                    it.startsWith("Error:")
                            && it.contains("cause:")
                }
            )
        )
    }

    @Test
    fun getShowListOnNoData(){
        TestingUtil.defaultShowRepo = ShowEmptyRepo
        // Assert RecyclerView should be gone.
        onView(withId(R.id.rv)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert loading progress should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView is displayed with text same as R.string.no_data.
        val strNoData = ApplicationProvider.getApplicationContext<Context>().getString(R.string.no_data)
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(strNoData)
            )
        )
    }
}