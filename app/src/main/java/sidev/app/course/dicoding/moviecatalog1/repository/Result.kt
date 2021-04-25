package sidev.app.course.dicoding.moviecatalog1.repository

sealed class Result<T>

data class Success<T>(val data: T): Result<T>()
data class Failure<T>(val code: Int, val e: Exception?): Result<T>()