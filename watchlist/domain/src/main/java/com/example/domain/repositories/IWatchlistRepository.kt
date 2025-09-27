package com.example.domain.repositories

import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import kotlinx.coroutines.flow.Flow

interface IWatchlistRepository {
    suspend fun getUserMovies() : Flow<WatchlistResult<WatchlistItemsResponse>>
    suspend fun getUserTvShows() : Flow<WatchlistResult<WatchlistItemsResponse>>
    suspend fun removeMovieFromWatchlist(mediaId: Int): WatchlistResult<Unit>
    suspend fun removeTvShowFromWatchlist(mediaId: Int): WatchlistResult<Unit>
    suspend fun removeMultipleMoviesFromWatchlist(mediaIds: List<Int>): WatchlistResult<Unit>
    suspend fun removeMultipleTvShowsFromWatchlist(mediaIds: List<Int>): WatchlistResult<Unit>
}