package com.example.user_preferences.favorites

import com.example.user_preferences.models.FireBaseMediaItem
import com.example.user_preferences.models.WatchlistResult

interface IWatchlistDataSource {

    // Movie watchlist operations
    suspend fun addMovieToWatchlist(mediaItem: FireBaseMediaItem): WatchlistResult<Unit>
    suspend fun removeMovieFromWatchlist(mediaId: Int): WatchlistResult<Unit>
    suspend fun getUserMovieWatchlist(): WatchlistResult<List<FireBaseMediaItem>>
    suspend fun isMovieInWatchlist(mediaId: Int): WatchlistResult<Boolean>
    suspend fun getUserMovieWatchlistCount(): WatchlistResult<Int>

    // TV show watchlist operations
    suspend fun addTvShowToWatchlist(mediaItem: FireBaseMediaItem): WatchlistResult<Unit>
    suspend fun removeTvShowFromWatchlist(mediaId: Int): WatchlistResult<Unit>
    suspend fun getUserTvShowWatchlist(): WatchlistResult<List<FireBaseMediaItem>>
    suspend fun isTvShowInWatchlist(mediaId: Int): WatchlistResult<Boolean>
    suspend fun getUserTvShowWatchlistCount(): WatchlistResult<Int>
    suspend fun getUserTotalWatchlistCount(): WatchlistResult<Pair<Int, Int>> // (moviesCount, tvShowsCount)
}