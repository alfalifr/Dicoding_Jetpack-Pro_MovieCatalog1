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
    val tagline: String,
    val overview: String,
    val backdropImg: String,
): Serializable {
    fun backdropImgUrl_533x300(): String = Const.getImgUrl_533x300(backdropImg)
}
