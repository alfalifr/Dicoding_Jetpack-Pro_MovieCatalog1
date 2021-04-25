package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.TestingUtil
import sidev.app.course.dicoding.moviecatalog1.TestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.lib.console.prin
import sidev.lib.console.prine

class ShowDetailViewModelTest {

    private lateinit var vm: ShowDetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        vm = ShowDetailViewModel(null, TestingUtil.dummyShowItem, Const.ShowType.MOVIE)
    }

    @Test
    fun downloadShowDetail(){
        vm.onCallNotSuccess { code, e ->
            prine("onCallNotSuccess code= $code e= $e")
        }
        vm.downloadShowDetail()

        val data = vm.showDetail.waitForValue()
        assertNotNull(data)
        assert(data.overview.isNotBlank())
        prin(data)
    }
}