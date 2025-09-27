package com.example.domain.models

import com.example.core_domain.CustomError

// MediaError class to hold title and subtitle string resource IDs
data class WatchlistError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)

// Custom result class similar to AuthResult
sealed class WatchlistResult<out T> {
    data class Success<T>(val data: T) : WatchlistResult<T>()
    data class Error(val error: WatchlistError) : WatchlistResult<Nothing>()
}

// Base domain DTO for media items in lists
data class WatchlistItem(
    val id: Int,
    val title: String,
    val rating: Double,
    val image: String,
    val horizontalImage: String? = null,
)

// Response DTOs for API responses
data class WatchlistItemsResponse(
    val items: List<WatchlistItem>
)
