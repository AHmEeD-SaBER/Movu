package com.example.domain.usecases.watchlisttvshows

import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import com.example.domain.repositories.IWatchlistRepository
import kotlinx.coroutines.flow.Flow

class GetWatchlistTvShows(
    private val repository: IWatchlistRepository
) : IGetWatchlistTvShows {
    override suspend fun invoke(): Flow<WatchlistResult<WatchlistItemsResponse>> {
        return repository.getUserTvShows()
    }
}