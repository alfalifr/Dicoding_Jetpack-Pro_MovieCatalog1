package sidev.app.course.dicoding.moviecatalog1.model

import java.io.Serializable

data class ShowDetail(
    val show: Show,
    val genre: String,
    val duration: String,
    val ageRating: String,
    val overview: String,
    val roles: List<String>,
): Serializable
