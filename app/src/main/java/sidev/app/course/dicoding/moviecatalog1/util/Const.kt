package sidev.app.course.dicoding.moviecatalog1.util

import sidev.app.course.dicoding.moviecatalog1.BuildConfig
import java.io.Serializable

object Const {
    enum class ShowType: Serializable {
        TV {
            override fun getDetailUrl(id: String, lang: String): String = getTvDetailUrl(id, lang)
            override fun getPopularUrl(lang: String, page: Int): String = getTvPopularUrl(lang, page)
        },
        MOVIE {
            override fun getDetailUrl(id: String, lang: String): String = getMovieDetailUrl(id, lang)
            override fun getPopularUrl(lang: String, page: Int): String = getMoviePopularUrl(lang, page)
        },
        ;

        abstract fun getDetailUrl(id: String, lang: String = "en-US"): String
        abstract fun getPopularUrl(lang: String = "en-US", page: Int = 1): String
    }

    const val SHARED_PREF_NAME = "_default_"
    const val ENDPOINT_ROOT = "https://api.themoviedb.org/3"
    const val ENDPOINT_MOVIE = "$ENDPOINT_ROOT/movie"
    const val ENDPOINT_TV = "$ENDPOINT_ROOT/tv"
    const val ENDPOINT_PUBLIC = "https://themoviedb.org"

    const val ENDPOINT_IMG = "$ENDPOINT_PUBLIC/t/p"
    const val ENDPOINT_IMG_220x330 = "$ENDPOINT_IMG/w220_and_h330_face"
    const val ENDPOINT_IMG_300x450 = "$ENDPOINT_IMG/w300_and_h450_bestv2"
    const val ENDPOINT_IMG_533x300 = "$ENDPOINT_IMG/w533_and_h300_bestv2"

    val API_KEY: String
        get()= BuildConfig.API_KEY

    fun getImgUrl_220x330(fileName: String): String = "$ENDPOINT_IMG_220x330/$fileName"
    fun getImgUrl_300x450(fileName: String): String = "$ENDPOINT_IMG_300x450/$fileName"
    fun getImgUrl_533x300(fileName: String): String = "$ENDPOINT_IMG_533x300/$fileName"

    fun getMovieDetailUrl(id: String, lang: String = "en-US"): String =
        "$ENDPOINT_MOVIE/$id?api_key=$API_KEY&language=$lang"
    //https://api.themoviedb.org/3/movie/458576?api_key=2c0f358fb74aa907fcc1f8adca770ac3&language=en-US
    fun getMoviePopularUrl(lang: String = "en-US", page: Int = 1): String =
        "$ENDPOINT_MOVIE/popular?api_key=$API_KEY&language=$lang&page=$page"

    fun getTvDetailUrl(id: String, lang: String = "en-US"): String =
        "$ENDPOINT_TV/$id?api_key=$API_KEY&language=$lang"

    fun getTvPopularUrl(lang: String = "en-US", page: Int = 1): String =
        "$ENDPOINT_TV/popular?api_key=$API_KEY&language=$lang&page=$page"


    const val KEY_TYPE = "type"
    const val KEY_SHOW = "show"
    const val KEY_RESULTS = "results"
    const val KEY_ID = "id"
    const val KEY_TITLE = "title"
    const val KEY_RELEASE = "release_date"
    const val KEY_FIRST_AIR_DATE = "first_air_date"
    const val KEY_IMG = "poster_path"
    const val KEY_RATING = "vote_average"
    const val KEY_GENRES = "genres"
    const val KEY_NAME = "name"
    const val KEY_TAGLINE = "tagline"
    const val KEY_OVERVIEW = "overview"
    const val KEY_BACKDROP = "backdrop_path"
    const val KEY_MOVIE_DURATION = "runtime"

    const val DEFAULT_TIMEOUT = 10000L //in millisecs
}