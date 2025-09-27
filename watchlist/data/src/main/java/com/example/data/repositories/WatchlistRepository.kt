package com.example.data.repositories

import com.example.core_data.utils.INetworkMonitor
import com.example.data.utils.toDomainWatchlistResult
import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult
import com.example.domain.repositories.IWatchlistRepository
import com.example.user_preferences.favorites.IWatchlistDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.R
import com.example.user_preferences.models.WatchlistResult as FirebaseWatchlistResult

class WatchlistRepository(
    private val fireBaseDataSource: IWatchlistDataSource,
    private val networkMonitor: INetworkMonitor
) : IWatchlistRepository {
    override suspend fun getUserMovies(): Flow<WatchlistResult<WatchlistItemsResponse>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                WatchlistResult.Error(
                    com.example.domain.models.WatchlistError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        val result = fireBaseDataSource.getUserMovieWatchlist()
        emit(result.toDomainWatchlistResult())


    }

    override suspend fun getUserTvShows(): Flow<WatchlistResult<WatchlistItemsResponse>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                WatchlistResult.Error(
                    com.example.domain.models.WatchlistError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        val result = fireBaseDataSource.getUserTvShowWatchlist()
        emit(result.toDomainWatchlistResult())


    }

    override suspend fun removeMovieFromWatchlist(mediaId: Int): WatchlistResult<Unit> {
        if (!networkMonitor.isNetworkAvailable()) {
            return WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )
            )
        }

        val result = fireBaseDataSource.removeMovieFromWatchlist(mediaId)
        return when (result) {
            is FirebaseWatchlistResult.Success -> WatchlistResult.Success(Unit)
            is FirebaseWatchlistResult.Error -> WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = result.error.titleRes,
                    subtitleRes = result.error.subtitleRes
                )
            )
        }
    }

    override suspend fun removeTvShowFromWatchlist(mediaId: Int): WatchlistResult<Unit> {
        if (!networkMonitor.isNetworkAvailable()) {
            return WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )
            )
        }

        val result = fireBaseDataSource.removeTvShowFromWatchlist(mediaId)
        return when (result) {
            is FirebaseWatchlistResult.Success -> WatchlistResult.Success(Unit)
            is FirebaseWatchlistResult.Error -> WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = result.error.titleRes,
                    subtitleRes = result.error.subtitleRes
                )
            )
        }
    }

    override suspend fun removeMultipleMoviesFromWatchlist(mediaIds: List<Int>): WatchlistResult<Unit> {
        if (!networkMonitor.isNetworkAvailable()) {
            return WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )
            )
        }

        // Remove each movie individually
        for (mediaId in mediaIds) {
            val result = fireBaseDataSource.removeMovieFromWatchlist(mediaId)
            if (result is FirebaseWatchlistResult.Error) {
                return WatchlistResult.Error(
                    com.example.domain.models.WatchlistError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    )
                )
            }
        }
        return WatchlistResult.Success(Unit)
    }

    override suspend fun removeMultipleTvShowsFromWatchlist(mediaIds: List<Int>): WatchlistResult<Unit> {
        if (!networkMonitor.isNetworkAvailable()) {
            return WatchlistResult.Error(
                com.example.domain.models.WatchlistError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )
            )
        }

        // Remove each TV show individually
        for (mediaId in mediaIds) {
            val result = fireBaseDataSource.removeTvShowFromWatchlist(mediaId)
            if (result is FirebaseWatchlistResult.Error) {
                return WatchlistResult.Error(
                    com.example.domain.models.WatchlistError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    )
                )
            }
        }
        return WatchlistResult.Success(Unit)
    }
}