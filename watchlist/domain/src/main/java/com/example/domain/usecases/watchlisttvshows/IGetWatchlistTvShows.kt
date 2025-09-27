package com.example.domain.usecases.watchlisttvshows

import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import kotlinx.coroutines.flow.Flow

interface IGetWatchlistTvShows {
    suspend operator fun invoke(): Flow<WatchlistResult<WatchlistItemsResponse>>
}