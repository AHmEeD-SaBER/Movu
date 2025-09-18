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
    val image: String
)

interface MediaDetails {
    val id: Int
    val title: String
    val image: String
    val genres: List<String>
    val productionCompanies: List<String>
    val rating: Double
    val languages: List<String>
    val plot: String
}

data class Movie(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val genres: List<String>,
    override val productionCompanies: List<String>,
    val length: Int,
    override val rating: Double,
    override val languages: List<String>,
    override val plot: String
) : MediaDetails

data class Tv(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val genres: List<String>,
    override val productionCompanies: List<String>,
    override val rating: Double,
    override val languages: List<String>,
    override val plot: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int
) : MediaDetails


// Response DTOs for API responses
data class MediaItemsResponse(
    val items: List<MediaItem>
)
