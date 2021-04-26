package sidev.app.course.dicoding.moviecatalog1.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo

/**
 * Value inside this class should be modified in testing process, e.g. unit / instrumented testing.
 */
object Config {
    private val _idlingRes by lazy { CountingIdlingResource("GLOBAL") }

    var defaultShowRepo: ShowRepo = ShowApiRepo

    fun resetDefautlShowRepo(){
        defaultShowRepo = ShowApiRepo
    }

    var isTest = false

    val idlingRes: IdlingResource?
        get()= if(isTest) _idlingRes else null

    fun incIdling(){
        if(isTest)
            _idlingRes.increment()
    }
    fun decIdling(){
        if(isTest)
            _idlingRes.decrement()
    }
}