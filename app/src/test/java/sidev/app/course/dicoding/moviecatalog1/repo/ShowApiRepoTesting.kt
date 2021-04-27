package sidev.app.course.dicoding.moviecatalog1.repo

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success

class ShowApiRepoTesting {

    private val repo = ShowApiRepo

    @Test
    fun getPopularMovieList(): Unit = runBlocking {
        val list = repo.getPopularMovieList(null)
        assert(list is Success)
        (list as Success).data.verify()
    }

    @Test
    fun getPopularTvList(): Unit = runBlocking {
        val list = repo.getPopularTvList(null)
        assert(list is Success)
        (list as Success).data.verify()
    }

    /**
     * Data as of 26 April 2021.
     */
    @Test
    fun getMovieDetail(): Unit = runBlocking {
        val monsterHunterId= "458576"
        val monsterHunterTitle= "Monster Hunter"
        val monsterHunterTagline= "Behind our world, there is another."
        val monsterHunterRelease= "2020-12-03"

        val detail = repo.getMovieDetail(null, monsterHunterId)
        assert(detail is Success)
        (detail as Success).data.apply {
            assertEquals(monsterHunterId, show.id)
            assertEquals(monsterHunterTitle, show.title)
            assertEquals(monsterHunterRelease, show.release)
            assertEquals(monsterHunterTagline, tagline)
            assert(overview.isNotBlank())
        }
    }

    /**
     * Data as of 26 April 2021.
     */
    @Test
    fun getTvDetail(): Unit = runBlocking {
        val aotId = "1429"
        val aotTitle = "Attack on Titan"
        val aotRelease = "2013-04-07"
        val aotOverviewPart = "humans were nearly exterminated by Titans"

        val detail = repo.getTvDetail(null, aotId)
        assert(detail is Success)
        (detail as Success).data.apply {
            assertEquals(aotId, show.id)
            assertEquals(aotTitle, show.title)
            assertEquals(aotRelease, show.release)
            assert(overview.isNotBlank())
            assert(overview.contains(aotOverviewPart))
        }
    }


    private fun List<Show>.verify(){
        assertNotNull(this)
        assert(isNotEmpty())

        val range = indices
        val randomId = this[range.random()].id
        val randomImg = this[range.random()].img
        val randomTitle = this[range.random()].title
        val randomRelease = this[range.random()].release

        assert(randomId.isNotBlank())
        assert(randomImg.startsWith("/"))
        assert(randomTitle.isNotBlank())
        assert(randomRelease.isNotBlank())
    }
}