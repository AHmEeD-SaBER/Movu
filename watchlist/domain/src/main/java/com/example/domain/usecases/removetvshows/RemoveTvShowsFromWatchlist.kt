package com.example.domain.usecases.removetvshows

import com.example.domain.models.WatchlistResult
import com.example.domain.repositories.IWatchlistRepository

class RemoveTvShowsFromWatchlist(
    private val repository: IWatchlistRepository
) : IRemoveTvShowsFromWatchlist {
    override suspend fun invoke(mediaIds: List<Int>): WatchlistResult<Unit> {
        return repository.removeMultipleTvShowsFromWatchlist(mediaIds)
    }
}
