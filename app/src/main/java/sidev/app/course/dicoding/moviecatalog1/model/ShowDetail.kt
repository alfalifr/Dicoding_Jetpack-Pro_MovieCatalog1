package sidev.app.course.dicoding.moviecatalog1.model

import sidev.app.course.dicoding.moviecatalog1.util.Const
import java.io.Serializable

/**
 * For both TV Show and Movie because they have same structure.
 */
data class ShowDetail(
    val show: Show,
    val genres: List<String>,
    val duration: Int?, //in minutes, null if the show type is tv show
    //val ageRating: String,
    val tagline: String,
    val overview: String,
    val backdropImg: String,
): Serializable {
    fun backdropImgUrl_220x330(): String = Const.getImgUrl_220x330(backdropImg)
    fun backdropImgUrl_300x450(): String = Const.getImgUrl_300x450(backdropImg)
    fun backdropImgUrl_533x300(): String = Const.getImgUrl_533x300(backdropImg)
}
