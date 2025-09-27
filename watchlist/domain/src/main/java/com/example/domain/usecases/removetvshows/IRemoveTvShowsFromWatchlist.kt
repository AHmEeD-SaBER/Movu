package com.example.domain.usecases.removetvshows

import com.example.domain.models.WatchlistResult

interface IRemoveTvShowsFromWatchlist {
    suspend operator fun invoke(mediaIds: List<Int>): WatchlistResult<Unit>
}
