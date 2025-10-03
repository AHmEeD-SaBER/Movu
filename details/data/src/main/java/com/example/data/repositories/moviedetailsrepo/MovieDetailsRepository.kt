package com.example.data.repositories.moviedetailsrepo

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.core_data.R
import com.example.data.data_sources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.data_sources.videosdatasource.IVideosDataSource
import com.example.data.utils.toDomainModel
import com.example.data.utils.toCredits
import com.example.data.utils.getTrailerLink
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Movie
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.ReviewItem
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.example.user_preferences.reviews.IReviewDataSource
import com.example.user_preferences.models.FireBaseMediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine

class MovieDetailsRepository(
    private val dataSource: IMovieDetailsDataSource,
    private val creditsDataSource: ICreditsDataSource,
    private val networkMonitor: INetworkMonitor,
    private val videosDataSource: IVideosDataSource,
    private val watchlistDataSource: IWatchlistDataSource,
    private val reviewDataSource: IReviewDataSource
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

    override suspend fun addMovieReview(reviewItem: ReviewItem): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.addMovieReview(
                mediaId = reviewItem.mediaId,
                rating = reviewItem.rating,
                reviewText = reviewItem.reviewText
            )) {
                is com.example.user_preferences.models.ReviewResult.Success ->
                    ReviewResult.Success(Unit)
                is com.example.user_preferences.models.ReviewResult.Error ->
                    ReviewResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            ReviewResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun updateMovieReview(reviewItem: ReviewItem): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.updateMovieReview(
                mediaId = reviewItem.mediaId,
                rating = reviewItem.rating,
                reviewText = reviewItem.reviewText
            )) {
                is com.example.user_preferences.models.ReviewResult.Success ->
                    ReviewResult.Success(Unit)
                is com.example.user_preferences.models.ReviewResult.Error ->
                    ReviewResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            ReviewResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun deleteMovieReview(movieId: Int): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.deleteMovieReview(movieId)) {
                is com.example.user_preferences.models.ReviewResult.Success ->
                    ReviewResult.Success(Unit)
                is com.example.user_preferences.models.ReviewResult.Error ->
                    ReviewResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            ReviewResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun getMovieReviews(movieId: Int): ReviewResult<List<Review>> {
        return try {
            when (val result = reviewDataSource.getMovieReviews(movieId)) {
                is com.example.user_preferences.models.ReviewResult.Success -> {
                    val reviews = result.data.map { it.toDomainModel() }
                    ReviewResult.Success(reviews)
                }
                is com.example.user_preferences.models.ReviewResult.Error ->
                    ReviewResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            ReviewResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }

    override suspend fun getUserMovieReview(movieId: Int): ReviewResult<Review?> {
        return try {
            when (val result = reviewDataSource.getUserMovieReview(movieId)) {
                is com.example.user_preferences.models.ReviewResult.Success -> {
                    val review = result.data?.toDomainModel()
                    ReviewResult.Success(review)
                }
                is com.example.user_preferences.models.ReviewResult.Error ->
                    ReviewResult.Error(DetailsError(
                        titleRes = result.error.titleRes,
                        subtitleRes = result.error.subtitleRes
                    ))
            }
        } catch (e: Exception) {
            ReviewResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            ))
        }
    }
}
