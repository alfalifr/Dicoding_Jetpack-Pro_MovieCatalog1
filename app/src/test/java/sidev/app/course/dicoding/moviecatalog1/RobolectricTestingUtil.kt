package sidev.app.course.dicoding.moviecatalog1

import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.matcher.ViewMatchers as AndroidViewMathcers

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