package sidev.app.course.dicoding.moviecatalog1.repository

import android.content.Context
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.util.TestingUtil

object ShowDummyRepo: ShowRepo {
    override suspend fun getPopularMovieList(c: Context?): Result<List<Show>> = Success(listOf(TestingUtil.dummyShowItem))
    override suspend fun getPopularTvList(c: Context?): Result<List<Show>> = Success(listOf(TestingUtil.dummyShowItem))
    override suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail> = Success(TestingUtil.dummyShowDetail)
    override suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail> = Success(TestingUtil.dummyShowDetail)
}