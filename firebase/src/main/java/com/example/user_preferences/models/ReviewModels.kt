package com.example.user_preferences.models

import com.example.core_domain.CustomError

// ReviewError class following the same pattern as WatchlistError
data class ReviewError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)

// Custom result class for review operations
sealed class ReviewResult<out T> {
    data class Success<T>(val data: T) : ReviewResult<T>()
    data class Error(val error: ReviewError) : ReviewResult<Nothing>()
}

// Firebase review model for Firestore storage
data class FirebaseReview(
    val userId: String = "",
    val userName: String = "",
    val mediaId: Int = 0,
    val mediaType: String = "", // "movie" or "tv"
    val rating: Float = 0f, // Out of 5 stars
    val reviewText: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

