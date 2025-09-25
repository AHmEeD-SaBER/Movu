package com.example.user_preferences.models

import com.example.core_domain.CustomError

// WatchlistError class following the same pattern as MediaError
data class WatchlistError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)

// Custom result class similar to MediaResult
sealed class WatchlistResult<out T> {
    data class Success<T>(val data: T) : WatchlistResult<T>()
    data class Error(val error: WatchlistError) : WatchlistResult<Nothing>()
}

// Firebase mirror of MediaItem for Firestore storage
data class FireBaseMediaItem(
    val id: Int = 0,
    val title: String = "",
    val rating: Double = 0.0,
    val image: String = "",
    val horizontalImage: String? = null,
)
