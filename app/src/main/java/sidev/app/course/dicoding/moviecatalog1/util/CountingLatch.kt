package sidev.app.course.dicoding.moviecatalog1.util

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CountingLatch(initCount: Int = 1) {
    @Volatile
    private var lock= CountDownLatch(initCount)

    val count: Long
        get()= lock.count

    fun increment(){
        lock = CountDownLatch(lock.count.toInt() + 1)
    }
    fun decrement(){
        lock.countDown()
    }

    fun await(timeout: Long = Const.DEFAULT_TIMEOUT, timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Boolean =
        lock.await(timeout, timeUnit)
}