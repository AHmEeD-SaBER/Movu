package com.example.data.repositories.moviedetailsrepo

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.core_data.R
import com.example.data.data_sources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.data_sources.videosdatasource.IVideosDataSource
import com.example.details.data.utils.toDomainModel
import com.example.details.data.utils.toCredits
import com.example.details.data.utils.getTrailerLink
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Movie
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.example.user_preferences.models.FireBaseMediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine

class MovieDetailsRepository(
    private val dataSource: IMovieDetailsDataSource,
    private val creditsDataSource: ICreditsDataSource,
    private val networkMonitor: INetworkMonitor,
    private val videosDataSource: IVideosDataSource,
    private val watchlistDataSource: IWatchlistDataSource
) : IMovieDetailsRepository {
    override fun getMovieDetails(movieId: Int): Flow<DetailsResult<Movie>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(DetailsResult.Error(DetailsError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            combine(
                dataSource.getMovieDetails(movieId),
                creditsDataSource.getMovieCredits(movieId),
                videosDataSource.getMovieVideos(movieId)
            ) { movieDetailsResponse, creditsResponse, videosResponse ->
                if (movieDetailsResponse.id == null || movieDetailsResponse.title.isNullOrEmpty()) {
                    DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    ))
                } else {
                    val credits = creditsResponse.toCredits()
                    val trailerLink = videosResponse.getTrailerLink()
                    val domainMovie = movieDetailsResponse.toDomainModel(credits, trailerLink)
                    DetailsResult.Success(domainMovie)
                }
            }.collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            emit(DetailsResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            )))
        }
    }

    override suspend fun addMovieToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit> {
        return try {
            val firebaseItem = FireBaseMediaItem(
                id = watchlistItem.id,
                title = watchlistItem.title,
                rating = watchlistItem.rating,
                image = watchlistItem.image
            )

            when (val result = watchlistDataSource.addMovieToWatchlist(firebaseItem)) {
                is com.example.user_preferences.models.WatchlistResult.Success ->
                    WatchlistResult.Success(Unit)
                is com.example.user_preferences.models.WatchlistResult.Error ->
                    WatchlistResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            WatchlistResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun removeMovieFromWatchlist(movieId: Int): WatchlistResult<Unit> {
        return try {
            when (val result = watchlistDataSource.removeMovieFromWatchlist(movieId)) {
                is com.example.user_preferences.models.WatchlistResult.Success ->
                    WatchlistResult.Success(Unit)
                is com.example.user_preferences.models.WatchlistResult.Error ->
                    WatchlistResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            WatchlistResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun isMovieInWatchlist(movieId: Int): WatchlistResult<Boolean> {
        return try {
            when (val result = watchlistDataSource.isMovieInWatchlist(movieId)) {
                is com.example.user_preferences.models.WatchlistResult.Success ->
                    WatchlistResult.Success(result.data)
                is com.example.user_preferences.models.WatchlistResult.Error ->
                    WatchlistResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            WatchlistResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }
}
