package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import kotlinx.coroutines.flow.Flow

interface ITvDetailsRepository {
    fun getTvDetails(tvId: Int): Flow<DetailsResult<Tv>>

    // Watchlist operations
    suspend fun addTvToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit>
    suspend fun removeTvFromWatchlist(tvId: Int): WatchlistResult<Unit>
    suspend fun isTvInWatchlist(tvId: Int): WatchlistResult<Boolean>
}
