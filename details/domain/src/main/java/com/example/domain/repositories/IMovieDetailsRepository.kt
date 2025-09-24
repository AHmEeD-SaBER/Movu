package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Movie
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<DetailsResult<Movie>>

    // Watchlist operations
    suspend fun addMovieToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit>
    suspend fun removeMovieFromWatchlist(movieId: Int): WatchlistResult<Unit>
    suspend fun isMovieInWatchlist(movieId: Int): WatchlistResult<Boolean>
}
