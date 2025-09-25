package com.example.domain

// Domain models for watchlist functionality
data class WatchlistItem(
    val id: Int,
    val title: String,
    val rating: Double,
    val image: String
)

sealed class WatchlistResult<out T> {
    data class Success<T>(val data: T) : WatchlistResult<T>()
    data class Error(val error: DetailsError) : WatchlistResult<Nothing>()
}
