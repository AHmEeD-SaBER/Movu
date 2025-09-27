package com.example.domain.usecases.watchlistmovies

import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import kotlinx.coroutines.flow.Flow

interface IGetWatchlistMovies {
    suspend operator fun invoke(): Flow<WatchlistResult<WatchlistItemsResponse>>
}