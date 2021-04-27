package sidev.app.course.dicoding.moviecatalog1.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.AndroidTestingUtil
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.repository.ShowErrorRepo
import sidev.app.course.dicoding.moviecatalog1.ui.activity.MainActivity
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil

class DetailActivityTest {

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
    fun showDetail(){
        // First, click item 1 in RecyclerView to go to detail.
        onView(withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )
        // Here we go the DetailActivity
        // Assert loading progress bar should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert error TextView should be gone.
        onView(withId(R.id.tv_error)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert title is displayed and not template.
        val titleTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_title)
        onView(withId(R.id.tv_title)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                    it.isNotBlank() && it != titleTemplate
                }
            )
        )
        // Assert release date is displayed and not template.
        val relDatTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_date)
        onView(withId(R.id.tv_release)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                    it.isNotBlank() && it != relDatTemplate
                }
            )
        )
        // Assert genres is displayed and not template.
        val genresTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_genres)
        onView(withId(R.id.tv_genres)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                    it.isNotBlank() && it != genresTemplate
                }
            )
        )
        // Assert overview header is displayed.
        onView(withId(R.id.tv_overview)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        // Assert overview content is displayed and not template.
        val overviewTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.lorem_ipsum)
        onView(withId(R.id.tv_overview_content)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                    it.isNotBlank() && it != overviewTemplate
                }
            )
        )
    }

    @Test
    fun showDetailOnError(){
        // First, click item 1 in RecyclerView to go to detail.
        onView(withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                AndroidTestingUtil.ViewActions.clickAndBefore {
                    // Before moving to detail page, switch the repo first.
                    TestingUtil.defaultShowRepo = ShowErrorRepo
                }
            )
        )
        // Here we go the DetailActivity
        // Assert loading progress bar should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert error TextView is displayed with text starts with 'Error:' and contains 'cause:'.
        onView(withId(R.id.tv_error)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.textMatches {
                    it.startsWith("Error:")
                            && it.contains("cause:")
                }
            )
        )
        // Assert overview header should be gone.
        onView(withId(R.id.tv_overview)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert overview content should be gone.
        onView(withId(R.id.tv_overview_content)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
    }
}