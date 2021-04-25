package sidev.app.course.dicoding.moviecatalog1

import android.content.Context
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.repository.Result
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success

object EmptyShowRepo: ShowRepo {
    override suspend fun getPopularMovieList(c: Context?): Result<List<Show>> = Success(emptyList())
    override suspend fun getPopularTvList(c: Context?): Result<List<Show>> = Success(emptyList())
    override suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail> = Success(TestingUtil.dummyShowDetail)
    override suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail> = Success(TestingUtil.dummyShowDetail)
}