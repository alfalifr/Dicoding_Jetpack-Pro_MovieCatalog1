package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.UnitTestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil
import sidev.lib.console.prin
import sidev.lib.console.prine

class ShowDetailViewModelTest {

    private lateinit var vm: ShowDetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        vm = ShowDetailViewModel(null, ShowApiRepo, Const.ShowType.MOVIE)
    }

    @Test
    fun downloadShowDetail(){
        vm.onCallNotSuccess { code, e ->
            prine("onCallNotSuccess code= $code e= $e")
        }
        vm.downloadShowDetail(TestingUtil.dummyShowItem.id)

        val data = vm.showDetail.waitForValue()
        assertNotNull(data)
        assert(data.show.title.isNotBlank())
        assert(data.show.id.isNotBlank())
        assert(data.show.release.isNotBlank())
        assert(data.overview.isNotBlank())
        prin(data)
    }
}