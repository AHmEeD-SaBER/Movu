package com.example.data.repositories.tvdetailsrepo


import com.example.core_data.utils.INetworkMonitor
import com.example.core_data.R
import com.example.data.data_sources.tvdetailsdatasource.ITvDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.data_sources.videosdatasource.IVideosDataSource
import com.example.data.utils.toDomainModel
import com.example.data.utils.toCredits
import com.example.data.utils.getTrailerLink
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.ReviewItem
import com.example.domain.repositories.ITvDetailsRepository
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.example.user_preferences.reviews.IReviewDataSource
import com.example.user_preferences.models.FireBaseMediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine

class TvDetailsRepository(
    private val dataSource: ITvDetailsDataSource,
    private val creditsDataSource: ICreditsDataSource,
    private val networkMonitor: INetworkMonitor,
    private val videosDataSource: IVideosDataSource,
    private val watchlistDataSource: IWatchlistDataSource,
    private val reviewDataSource: IReviewDataSource
) : ITvDetailsRepository {
    override fun getTvDetails(tvId: Int): Flow<DetailsResult<Tv>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(DetailsResult.Error(DetailsError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            combine(
                dataSource.getTvDetails(tvId),
                creditsDataSource.getTvCredits(tvId),
                videosDataSource.getTvVideos(tvId)
            ) { tvDetailsResponse, creditsResponse, videosResponse ->
                if (tvDetailsResponse.id == null || tvDetailsResponse.name.isNullOrEmpty()) {
                    DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    ))
                } else {
                    val credits = creditsResponse.toCredits()
                    val trailerLink = videosResponse.getTrailerLink()
                    val domainTv = tvDetailsResponse.toDomainModel(credits, trailerLink)
                    DetailsResult.Success(domainTv)
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

    override suspend fun addTvToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit> {
        return try {
            val firebaseItem = FireBaseMediaItem(
                id = watchlistItem.id,
                title = watchlistItem.title,
                rating = watchlistItem.rating,
                image = watchlistItem.image
            )

            when (val result = watchlistDataSource.addTvShowToWatchlist(firebaseItem)) {
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

    override suspend fun removeTvFromWatchlist(tvId: Int): WatchlistResult<Unit> {
        return try {
            when (val result = watchlistDataSource.removeTvShowFromWatchlist(tvId)) {
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

    override suspend fun isTvInWatchlist(tvId: Int): WatchlistResult<Boolean> {
        return try {
            when (val result = watchlistDataSource.isTvShowInWatchlist(tvId)) {
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

    override suspend fun addTvReview(reviewItem: ReviewItem): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.addTvShowReview(
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

    override suspend fun updateTvReview(reviewItem: ReviewItem): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.updateTvShowReview(
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

    override suspend fun deleteTvReview(tvId: Int): ReviewResult<Unit> {
        return try {
            when (val result = reviewDataSource.deleteTvShowReview(tvId)) {
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

    override suspend fun getTvReviews(tvId: Int): ReviewResult<List<Review>> {
        return try {
            when (val result = reviewDataSource.getTvShowReviews(tvId)) {
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

    override suspend fun getUserTvReview(tvId: Int): ReviewResult<Review?> {
        return try {
            when (val result = reviewDataSource.getUserTvShowReview(tvId)) {
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
