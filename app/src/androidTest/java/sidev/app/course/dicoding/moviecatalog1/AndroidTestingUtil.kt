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
import sidev.lib.check.assertNotNull
import androidx.test.espresso.action.ViewActions as AndroidViewActions
import androidx.test.espresso.matcher.ViewMatchers as AndroidViewMathcers

object AndroidTestingUtil {
    object ViewActions {
        fun clickAndBefore(f: () -> Unit): ViewAction {
            val action = AndroidViewActions.click()
            return object : ViewAction by action {
                /**
                 * Performs this action on the given view.
                 *
                 * @param uiController the controller to use to interact with the UI.
                 * @param view the view to act upon. never null.
                 */
                override fun perform(uiController: UiController?, view: View?) {
                    f()
                    action.perform(uiController, view)
                }
            }
        }
    }
    object ViewMatchers {
        fun isNotDisplayed(): Matcher<View> {
            val matcher = AndroidViewMathcers.isDisplayed()
            return object: TypeSafeMatcher<View>(){
                override fun describeTo(description: Description?) {
                    description?.appendText("is NOT displayed on the screen to the user")
                }
                override fun matchesSafely(item: View?): Boolean = !matcher.matches(item)
            }
        }

        fun textMatches(predicate: (String) -> Boolean): Matcher<View> = object: TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("with text matches predicate()")
            }
            override fun matchesSafely(item: View?): Boolean = item is TextView && predicate(item.text.toString())
        }
        fun textMatchesAndDisplayed(predicate: (String) -> Boolean): Matcher<View> = object: TypeSafeMatcher<View>() {
            val displayedMatcher =  AndroidViewMathcers.isDisplayed()
            override fun describeTo(description: Description?) {
                description?.appendText("with text matches predicate()")
            }
            override fun matchesSafely(item: View?): Boolean =
                displayedMatcher.matches(item) && item is TextView && predicate(item.text.toString())
        }
    }
    object RecyclerViewAssertion {
        private fun templateChildAssertion(body: (RecyclerView) -> Unit): ViewAssertion =
            ViewAssertion { view, noViewFoundException ->
                if(noViewFoundException != null)
                    throw noViewFoundException

                assert(view is RecyclerView)
                body(view as RecyclerView)
            }

        fun isChildInPositionDisplayed(
            childPos: Int, matchers: Matcher<View>
        ): ViewAssertion = templateChildAssertion {
            val lm = it.layoutManager
            assertNotNull(lm)
            //Impossibly null, so IT IS SAFE to use !!
            // If lm is null, then below line WILL NEVER be executed because of assertion.
            val v = lm!!.findViewByPosition(childPos)
            assert(matchers.matches(v))
        }

        fun isChildIdInPositionDisplayed(
            childPos: Int,
            @IdRes childId: Int,
            matchers: Matcher<View>,
        ): ViewAssertion = templateChildAssertion {
            val lm = it.layoutManager
            assertNotNull(lm)
            //Impossibly null, so IT IS SAFE to use !!
            // If lm is null, then below line WILL NEVER be executed because of assertion.
            val v = lm!!.findViewByPosition(childPos)?.findViewById<View>(childId)
            assert(matchers.matches(v))
        }
    }
}