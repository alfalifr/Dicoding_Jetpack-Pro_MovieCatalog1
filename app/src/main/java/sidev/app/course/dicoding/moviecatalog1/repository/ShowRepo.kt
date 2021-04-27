package sidev.app.course.dicoding.moviecatalog1.repository

import android.content.Context
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail

interface ShowRepo {
    suspend fun getPopularMovieList(c: Context?): Result<List<Show>>
    suspend fun getPopularTvList(c: Context?): Result<List<Show>>
    suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail>
    suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail>
}