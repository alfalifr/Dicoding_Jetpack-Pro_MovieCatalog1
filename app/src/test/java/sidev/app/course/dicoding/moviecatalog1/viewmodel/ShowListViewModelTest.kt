package sidev.app.course.dicoding.moviecatalog1.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import sidev.app.course.dicoding.moviecatalog1.UnitTestingUtil.waitForValue
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.lib.console.prin
import sidev.lib.console.prine

class ShowListViewModelTest {

    private lateinit var vm: ShowListViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        vm = ShowListViewModel(null, ShowApiRepo, Const.ShowType.values().random())
    }

    @Test
    fun downloadShowPopularList() {
        vm.onCallNotSuccess { code, e ->
            prine("onCallNotSuccess code= $code e= $e")
        }
        vm.downloadShowPopularList()

        val list = vm.showList.waitForValue()
        assertNotNull(list)
        assert(list.isNotEmpty())

        val range = list.indices
        val randomId = list[range.random()].id
        val randomImg = list[range.random()].img
        val randomTitle = list[range.random()].title
        val randomRelease = list[range.random()].release

        assert(randomId.isNotBlank())
        assert(randomImg.startsWith("/"))
        assert(randomTitle.isNotBlank())
        assert(randomRelease.isNotBlank())

        val contentStr = list.joinToString(separator = "\n")
        prin(contentStr)
    }
}