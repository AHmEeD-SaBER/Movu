package com.example.domain

data class Review(
    val userId: String,
    val userName: String,
    val mediaId: Int,
    val rating: Float,
    val reviewText: String,
    val timestamp: Long
)

data class ReviewItem(
    val mediaId: Int,
    val rating: Float,
    val reviewText: String
)

sealed class ReviewResult<out T> {
    data class Success<T>(val data: T) : ReviewResult<T>()
    data class Error(val error: DetailsError) : ReviewResult<Nothing>()
}

