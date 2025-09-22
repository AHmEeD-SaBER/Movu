package com.example.domain.models

import com.example.core_domain.CustomError

// MediaError class to hold title and subtitle string resource IDs
data class MediaError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)

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
