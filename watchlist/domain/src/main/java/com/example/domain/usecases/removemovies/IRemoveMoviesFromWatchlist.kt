package com.example.domain.usecases.removemovies

import com.example.domain.models.WatchlistResult

interface IRemoveMoviesFromWatchlist {
    suspend operator fun invoke(mediaIds: List<Int>): WatchlistResult<Unit>
}
