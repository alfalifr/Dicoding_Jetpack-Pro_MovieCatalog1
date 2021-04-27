package sidev.app.course.dicoding.moviecatalog1.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo

/**
 * Value inside this class should be modified in testing process, e.g. unit / instrumented testing.
 */
object TestingUtil {
    enum class UiTestType {
        ESPRESSO, ROBOLECTRIC
    }

    private val _idlingRes by lazy { CountingIdlingResource("GLOBAL") }
    private val _lock by lazy { CountingLatch() }

    private var uiTestType = UiTestType.ESPRESSO

    var defaultShowRepo: ShowRepo = ShowApiRepo

    fun resetDefautlShowRepo(){
        defaultShowRepo = ShowApiRepo
    }

    var isUiAsyncTest = false

    val idlingRes: IdlingResource?
        get()= if(isUiAsyncTest) _idlingRes else null

    fun incUiAsync(){
        if(isUiAsyncTest) when(uiTestType){
            UiTestType.ESPRESSO -> _idlingRes.increment()
            UiTestType.ROBOLECTRIC -> _lock.increment()
        }
    }
    fun decUiAsync(){
        if(isUiAsyncTest) when(uiTestType){
            UiTestType.ESPRESSO -> _idlingRes.decrement()
            UiTestType.ROBOLECTRIC -> _lock.decrement()
        }
    }


    val dummyShowType = Const.ShowType.MOVIE
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
}