package sidev.app.course.dicoding.moviecatalog1

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import sidev.lib.android.std.tool.util.`fun`.loge
import sidev.lib.check.assertNotNull
import sidev.lib.console.prine
import androidx.test.espresso.matcher.ViewMatchers as AndroidViewMathcers
import androidx.test.espresso.action.ViewActions as AndroidViewActions

object RobolectricTestingUtil {
    object ViewMatchers {
        fun textMatchesAndDisplayed(predicate: (String) -> Boolean): Matcher<View> = object: TypeSafeMatcher<View>() {
            val displayedMatcher =  AndroidViewMathcers.isDisplayed()
            override fun describeTo(description: Description?) {
                description?.appendText("with text matches predicate()")
            }
            override fun matchesSafely(item: View?): Boolean =
                displayedMatcher.matches(item) && item is TextView && predicate(item.text.toString())
        }
    }
}