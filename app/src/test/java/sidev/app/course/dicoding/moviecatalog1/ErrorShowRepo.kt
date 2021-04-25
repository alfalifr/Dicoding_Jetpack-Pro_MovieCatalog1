package sidev.app.course.dicoding.moviecatalog1

import android.content.Context
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.repository.Failure
import sidev.app.course.dicoding.moviecatalog1.repository.Result
import sidev.app.course.dicoding.moviecatalog1.repository.ShowRepo
import sidev.app.course.dicoding.moviecatalog1.repository.Success
import java.io.IOException

object ErrorShowRepo: ShowRepo {
    override suspend fun getPopularMovieList(c: Context?): Result<List<Show>> = Failure(404, IOException("Not found"))
    override suspend fun getPopularTvList(c: Context?): Result<List<Show>> = Failure(-1, IOException("Unknown"))
    override suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail> = Failure(-1, IOException("Still unknown"))
    override suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail> = Failure(10, IOException("I don't even know what is that code"))
}