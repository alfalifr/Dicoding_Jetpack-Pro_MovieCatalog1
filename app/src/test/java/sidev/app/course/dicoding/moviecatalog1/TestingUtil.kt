package sidev.app.course.dicoding.moviecatalog1

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.lib.`val`.SuppressLiteral
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object TestingUtil {
    const val DEFAULT_ASYNC_TIMEOUT = 5000L

    val dummyShowItem = Show(
        id="458576",
        title="Monster Hunter",
        img="/1UCOF11QCw8kcqvce8LKOO6pimh.jpg",
        release="2020-12-03",
        rating=7.1
    )

    val dummyShowDetail = ShowDetail(
        dummyShowItem,
        listOf("Cooking", "Action"),
        145,
        "He keeps moving forward until all his enemies get destroyed",
        "When Erwin has his 'SASAGEYO', Eren has his 'TATAKAE'",
        "/yvKrycViRMQcIgdnjsM5JGNWU4Q.jpg"
    )

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