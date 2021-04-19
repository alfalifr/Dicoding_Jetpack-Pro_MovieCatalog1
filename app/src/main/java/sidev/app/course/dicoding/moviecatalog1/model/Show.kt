package sidev.app.course.dicoding.moviecatalog1.model

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
): Serializable
