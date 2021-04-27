package sidev.app.course.dicoding.moviecatalog1.repository

import android.content.Context
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDouble
import sidev.app.course.dicoding.moviecatalog1.util.Util.getIntOrNull
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.structure.data.value.Var
import sidev.lib.structure.data.value.nullableVarOf
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object ShowApiRepo: ShowRepo {
    override suspend fun getPopularMovieList(c: Context?): Result<List<Show>> = getPopularShowList(c, Const.ShowType.MOVIE)
    override suspend fun getPopularTvList(c: Context?): Result<List<Show>> = getPopularShowList(c, Const.ShowType.TV)

    override suspend fun getMovieDetail(c: Context?, id: String): Result<ShowDetail> = getShowDetail(c, Const.ShowType.MOVIE, id)
    override suspend fun getTvDetail(c: Context?, id: String): Result<ShowDetail> = getShowDetail(c, Const.ShowType.TV, id)

    private fun getPopularShowList(c: Context?, type: Const.ShowType): Result<List<Show>> {
        val lock = CountDownLatch(1)
        var shows: List<Show> = emptyList()
        val container = nullableVarOf<Result<*>>()
        Util.httpGet(
            c,
            type.getPopularUrl(),
            doOnNotSucces(lock, container)
        ){ _, content ->
            shows = content.parseShowList()
            lock.countDown()
        }
        return when {
            container.value != null -> {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                container.value as Result<List<Show>>
            }
            lock.await(Const.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).not() ->
                Failure(1, TimeoutException("Can't get list of shows."))
            else -> Success(shows)
        }
    }

    private fun getShowDetail(c: Context?, type: Const.ShowType, showId: String): Result<ShowDetail> {
        val lock = CountDownLatch(1)
        val container = nullableVarOf<Result<*>>()
        var showDetail: ShowDetail?= null
        Util.httpGet(
            c,
            type.getDetailUrl(showId),
            doOnNotSucces(lock, container)
        ) { _, content ->
            val json = JsonParser.parseString(content).asJsonObject
            val genreArray = json.getAsJsonArray(Const.KEY_GENRES)
            val genres = ArrayList<String>(genreArray.size())
            genreArray.forEach {
                genres += it.asJsonObject.getString(Const.KEY_NAME)
            }
            val show = json.parseShow()
            showDetail = ShowDetail(
                show, genres,
                json.getIntOrNull(Const.KEY_MOVIE_DURATION),
                json.getString(Const.KEY_TAGLINE),
                json.getString(Const.KEY_OVERVIEW),
                json.getString(Const.KEY_BACKDROP),
            )
            lock.countDown()
        }
        return when {
            container.value != null -> {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                container.value as Result<ShowDetail>
            }
            lock.await(Const.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).not() ->
                Failure(1, TimeoutException("Can't get show detail."))
            else -> Success(showDetail as ShowDetail)
        }
    }

    private fun doOnNotSucces(lock: CountDownLatch, container: Var<in Result<*>>) = { code: Int, e: Exception? ->
        container.value = Failure<Any>(code, e)
        lock.countDown()
    }

    private fun String.parseShowList(): List<Show> {
        val json = JsonParser.parseString(this).asJsonObject
        val jsonArray = json.getAsJsonArray(Const.KEY_RESULTS)
        val movies = ArrayList<Show>(jsonArray.size())
        for(i in 0 until jsonArray.size()){
            val movieJson = jsonArray[i].asJsonObject
            movies += movieJson.parseShow()
        }
        return movies
    }
    private fun JsonObject.parseShow(): Show = Show(
        getString(Const.KEY_ID),
        (if(has(Const.KEY_TITLE)) getString(Const.KEY_TITLE)
        else getString(Const.KEY_NAME)),
        getString(Const.KEY_IMG),
        (if(has(Const.KEY_RELEASE)) getString(Const.KEY_RELEASE)
        else getString(Const.KEY_FIRST_AIR_DATE)),
        getDouble(Const.KEY_RATING),
    )
}