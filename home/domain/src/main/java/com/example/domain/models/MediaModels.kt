package com.example.domain.models

// MediaError class to hold title and subtitle string resource IDs
data class MediaError(
    val titleRes: Int,
    val subtitleRes: Int
)

// Custom result class similar to AuthResult
sealed class MediaResult<out T> {
    data class Success<T>(val data: T) : MediaResult<T>()
    data class Error(val error: MediaError) : MediaResult<Nothing>()
}

// Base domain DTO for media items in lists
data class MediaItem(
    val id: Int,
    val title: String,
    val rating: Double,
    val image: String,
    val horizontalImage: String? = null,
)

// Response DTOs for API responses
data class MediaItemsResponse(
    val items: List<MediaItem>
)
