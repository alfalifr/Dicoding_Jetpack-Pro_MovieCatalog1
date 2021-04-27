package sidev.app.course.dicoding.moviecatalog1.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.RobolectricTestingUtil
import sidev.app.course.dicoding.moviecatalog1.repository.ShowDummyRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowErrorRepo
import sidev.app.course.dicoding.moviecatalog1.ui.activity.DetailActivity
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DetailActivityUnitTest {

    val textMatcher = RobolectricTestingUtil.ViewMatchers::textMatchesAndDisplayed

    // The activity creation line can't be located in @Before because
    // it depends on TestingUtil.defaultShowRepo set first.
    // So the best way is to gather bolierplate code in this method.
    private fun createActivity(): DetailActivity = Robolectric.buildActivity(
        DetailActivity::class.java,
        Intent().apply {
            putExtra(Const.KEY_SHOW, TestingUtil.dummyShowItem)
            putExtra(Const.KEY_TYPE, TestingUtil.dummyShowType)
        }
    )
        .create()
        .start()
        .resume()
        .visible()
        .get()

    @After
    fun finish(){
        TestingUtil.resetDefautlShowRepo()
    }

    @Test
    fun showDetail(){
        // Use dummy repo because Robolectric can't integrate with Espresso Idling Resource.
        TestingUtil.defaultShowRepo = ShowDummyRepo
        val data = TestingUtil.dummyShowDetail

        val act = createActivity()

        // Assert loading progres bar should be gone.
        val pb = act.findViewById<View>(R.id.pb_loading)
        assertNotNull(pb)
        assert(!ViewMatchers.isDisplayed().matches(pb))

        // Assert error TextView should be gone.
        val tvError = act.findViewById<TextView>(R.id.tv_error)
        assertNotNull(tvError)
        assert(!ViewMatchers.isDisplayed().matches(tvError))

        // Assert title is displayed and not template.
        val tvTitle = act.findViewById<TextView>(R.id.tv_title)
        val titleTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_title)
        assert(
            textMatcher { it == data.show.title && it != titleTemplate }.matches(tvTitle)
        )

        // Assert release date is displayed and not template.
        val tvRelease = act.findViewById<TextView>(R.id.tv_release)
        val relDatTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_date)
        assert(
            textMatcher { it.isNotBlank() && it != relDatTemplate }.matches(tvRelease)
        )

        // Assert genres is displayed and not template.
        val tvGenres = act.findViewById<TextView>(R.id.tv_genres)
        val genresTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.template_genres)
        assert(
            textMatcher { it == data.genres.joinToString() && it != genresTemplate }.matches(tvGenres)
        )

        // Assert overview header is displayed.
        val tvOverview = act.findViewById<TextView>(R.id.tv_overview)
        assert(
            ViewMatchers.isDisplayed().matches(tvOverview)
        )

        // Assert overview content is displayed and not template.
        val tvOverviewContent = act.findViewById<TextView>(R.id.tv_overview_content)
        val overviewTemplate = ApplicationProvider.getApplicationContext<Context>().getString(R.string.lorem_ipsum)
        assert(
            textMatcher { it == data.overview && it != overviewTemplate }.matches(tvOverviewContent)
        )
    }

    @Test
    fun showDetailOnError(){
        TestingUtil.defaultShowRepo = ShowErrorRepo
        val act = createActivity()

        // Assert loading progress bar should be gone.
        val pb = act.findViewById<View>(R.id.pb_loading)
        assertNotNull(pb)
        assert(!ViewMatchers.isDisplayed().matches(pb))

        // Assert error TextView is displayed with text starts with 'Error:' and contains 'cause:'.
        val tvError = act.findViewById<TextView>(R.id.tv_error)
        assert(
            textMatcher {
                it.startsWith("Error:")
                        && it.contains("cause:")
            }.matches(tvError)
        )

        // Assert overview header should be gone.
        val tvOverview = act.findViewById<TextView>(R.id.tv_overview)
        assert(!ViewMatchers.isDisplayed().matches(tvOverview))

        // Assert overview content should be gone.
        val tvOverviewContent = act.findViewById<TextView>(R.id.tv_overview_content)
        assert(!ViewMatchers.isDisplayed().matches(tvOverviewContent))
    }
}