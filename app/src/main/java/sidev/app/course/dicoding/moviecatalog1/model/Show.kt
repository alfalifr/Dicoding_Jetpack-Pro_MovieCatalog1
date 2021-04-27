package sidev.app.course.dicoding.moviecatalog1.model

import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import java.io.Serializable

/**
 * For both TV Show and Movie because they have same structure.
 */
data class Show(
    val id: String,
    val title: String,
    val img: String,
    val release: String,
    val rating: Double,
): Serializable {
    fun imgUrl_300x450(): String = Const.getImgUrl_300x450(img)
    fun getFormattedDate(): String = Util.formatDate(release)
}
