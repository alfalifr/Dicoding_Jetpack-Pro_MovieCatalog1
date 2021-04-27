package sidev.app.course.dicoding.moviecatalog1.ui

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import sidev.app.course.dicoding.moviecatalog1.RobolectricTestingUtil
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.repository.ShowEmptyRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowErrorRepo
import sidev.app.course.dicoding.moviecatalog1.ui.activity.MainActivity
import sidev.app.course.dicoding.moviecatalog1.util.Config

@RunWith(AndroidJUnit4::class)
@org.robolectric.annotation.Config(sdk = [Build.VERSION_CODES.P])
class MainActivityUnitTest {

    @get:Rule
    val actRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        Config.isTest = true
        IdlingRegistry.getInstance().register(Config.idlingRes)
    }

    @After
    fun finish(){
        IdlingRegistry.getInstance().unregister(Config.idlingRes)
    }

    @Test
    fun robo(){
        val act = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .start()
            .resume()
            .visible()
            .get()

        getShowList()
    }
    
    @Test
    fun getShowList(){
        onView(withId(R.id.rv)).apply {
            // Assert RecyclerView is displayed and not empty
            check(
                RobolectricTestingUtil.RecyclerViewAssertion.isChildInPositionDisplayed(
                    0, ViewMatchers.isDisplayed()
                )
            )
            // Assert title is displayed and not template
            val strTitle = ApplicationProvider.getApplicationContext<Context>().getString(R.string.title)
            check(
                RobolectricTestingUtil.RecyclerViewAssertion.isChildIdInPositionDisplayed(
                    0, R.id.tv_title,
                    RobolectricTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                        it.isNotBlank() && it != strTitle
                    }
                )
            )
            // Assert release date is displayed and not template
            val relDatTitle = ApplicationProvider.getApplicationContext<Context>().getString(R.string.release_date)
            check(
                RobolectricTestingUtil.RecyclerViewAssertion.isChildIdInPositionDisplayed(
                    0, R.id.tv_release,
                    RobolectricTestingUtil.ViewMatchers.textMatchesAndDisplayed {
                        it.isNotBlank() && it != relDatTitle
                    }
                )
            )
        }
        // Assert loading progress bar should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView should be gone.
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
    }

    @Test
    fun getShowListOnError(){
        Config.defaultShowRepo = ShowErrorRepo
        // Assert RecyclerView should be gone.
        onView(withId(R.id.rv)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert loading progress should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView is displayed with text starts with 'Error:' and contains 'cause:'.
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.textMatches {
                    it.startsWith("Error:")
                            && it.contains("cause:")
                }
            )
        )
        Config.resetDefautlShowRepo()
    }

    @Test
    fun getShowListOnNoData(){
        Config.defaultShowRepo = ShowEmptyRepo
        // Assert RecyclerView should be gone.
        onView(withId(R.id.rv)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert loading progress should be gone.
        onView(withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                RobolectricTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
        // Assert no data TextView is displayed with text same as R.string.no_data.
        val strNoData = ApplicationProvider.getApplicationContext<Context>().getString(R.string.no_data)
        onView(withId(R.id.tv_no_data)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(strNoData)
            )
        )
        Config.resetDefautlShowRepo()
    }
}