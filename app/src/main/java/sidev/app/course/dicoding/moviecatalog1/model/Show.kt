package sidev.app.course.dicoding.moviecatalog1.model

import java.io.Serializable

data class Show(
    val id: String,
    val title: String,
    val img: String,
    val release: String,
    val rating: String,
): Serializable
