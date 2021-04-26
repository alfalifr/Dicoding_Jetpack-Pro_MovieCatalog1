package sidev.app.course.dicoding.moviecatalog1

import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.assertNotNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object UnitTestingUtil {
    const val DEFAULT_ASYNC_TIMEOUT = 10000L

    fun <T> LiveData<T>.waitForValue(
        timeout: Long = DEFAULT_ASYNC_TIMEOUT,
        timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    ): T {
        val lock = CountDownLatch(1)
        var data: T? = null
        val observer = object: Observer<T> {
            /**
             * Called when the data is changed.
             * @param t  The new data
             */
            override fun onChanged(t: T) {
                data = t
                removeObserver(this)
                lock.countDown()
            }
        }
        observeForever(observer)

        if(!lock.await(timeout, timeUnit)){
            throw TimeoutException("The value was never set.")
        }

        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return data as T
    }
}