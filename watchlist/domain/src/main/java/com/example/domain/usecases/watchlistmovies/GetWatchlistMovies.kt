package com.example.domain.usecases.watchlistmovies

import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import com.example.domain.repositories.IWatchlistRepository
import kotlinx.coroutines.flow.Flow

class GetWatchlistMovies(
    private val repository: IWatchlistRepository
) : IGetWatchlistMovies {
    override suspend fun invoke(): Flow<WatchlistResult<WatchlistItemsResponse>> {
        return repository.getUserMovies()
    }
}