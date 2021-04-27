package sidev.app.course.dicoding.moviecatalog1.util

import java.util.concurrent.CountDownLatch

class CountingLatch(initCount: Int = 1) {
    @Volatile
    private var lock= CountDownLatch(initCount)

    fun increment(){
        lock = CountDownLatch(lock.count.toInt() + 1)
    }
    fun decrement(){
        lock.countDown()
    }

}