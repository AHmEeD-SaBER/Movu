package com.example.domain.usecases.removemovies

import com.example.domain.models.WatchlistResult
import com.example.domain.repositories.IWatchlistRepository

class RemoveMoviesFromWatchlist(
    private val repository: IWatchlistRepository
) : IRemoveMoviesFromWatchlist {
    override suspend fun invoke(mediaIds: List<Int>): WatchlistResult<Unit> {
        return repository.removeMultipleMoviesFromWatchlist(mediaIds)
    }
}
