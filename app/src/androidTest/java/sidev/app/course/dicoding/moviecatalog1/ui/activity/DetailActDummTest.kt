package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.AndroidTestingUtil
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil

class DetailActDummTest {
    @get:Rule
    //val actRule = ActivityTestRule(DetailActivity::class.java, true, false)
    ///*
    val actRule = ActivityScenarioRule(DetailActivity::class.java,
        bundleOf(
            Const.KEY_SHOW to TestingUtil.dummyShowItem,
            Const.KEY_TYPE to TestingUtil.dummyShowType,
        )
    )
    // */

    @Before
    fun setup(){
/*
        actRule.launchActivity(
            Intent().apply {
                putExtra(Const.KEY_SHOW, TestingUtil.dummyShowItem)
                putExtra(Const.KEY_TYPE, TestingUtil.dummyShowType)
            }
        )
 */
    }

    @Test
    fun test(){
        Espresso.onView(ViewMatchers.withId(R.id.pb_loading)).check(
            ViewAssertions.matches(
                AndroidTestingUtil.ViewMatchers.isNotDisplayed()
            )
        )
    }
}