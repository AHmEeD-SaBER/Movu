package com.example.data.repositories

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.ProfileDomainUser
import com.example.domain.models.UserResult
import com.example.domain.models.UserError
import com.example.domain.models.ReviewStatistics
import com.example.domain.repositories.IUserRepository
import com.example.user_preferences.auth.IAuthDataSource
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.example.user_preferences.reviews.IReviewDataSource
import com.example.data.utils.toProfileDomainUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.R
import com.example.data.utils.toDomainWatchlistResult

class UserRepository(
    private val authDataSource: IAuthDataSource,
    private val watchlistDataSource: IWatchlistDataSource,
    private val reviewDataSource: IReviewDataSource,
    private val networkMonitor: INetworkMonitor
) : IUserRepository {

    override suspend fun getCurrentUser(): Flow<UserResult<ProfileDomainUser>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        try {
            val currentUser = authDataSource.getCurrentUser()
            if (currentUser != null) {
                emit(UserResult.Success(currentUser.toProfileDomainUser()))
            } else {
                emit(
                    UserResult.Error(
                        UserError(
                            titleRes = R.string.error_title_auth,
                            subtitleRes = R.string.error_subtitle_auth
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_generic,
                        subtitleRes = R.string.error_subtitle_generic
                    )
                )
            )
        }
    }

    override suspend fun getUserWatchListCount(): Flow<UserResult<Pair<Int, Int>>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        val result = watchlistDataSource.getUserTotalWatchlistCount()
        emit(result.toDomainWatchlistResult())
    }

    override suspend fun logout(): Flow<UserResult<Unit>> = flow {
        try {
            authDataSource.signOut()
            emit(UserResult.Success(Unit))
        } catch (e: Exception) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_generic,
                        subtitleRes = R.string.error_subtitle_generic
                    )
                )
            )
        }
    }

    override suspend fun getUserReviewStatistics(): Flow<UserResult<ReviewStatistics>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        try {
            val totalCountResult = reviewDataSource.getUserReviewCount()
            val movieCountResult = reviewDataSource.getUserMovieReviewCount()
            val tvCountResult = reviewDataSource.getUserTvShowReviewCount()
            val allReviewsResult = reviewDataSource.getAllUserReviews()

            when {
                totalCountResult is com.example.user_preferences.models.ReviewResult.Success &&
                movieCountResult is com.example.user_preferences.models.ReviewResult.Success &&
                tvCountResult is com.example.user_preferences.models.ReviewResult.Success &&
                allReviewsResult is com.example.user_preferences.models.ReviewResult.Success -> {

                    val averageRating = if (allReviewsResult.data.isNotEmpty()) {
                        allReviewsResult.data.map { it.rating }.average().toFloat()
                    } else {
                        0f
                    }

                    val statistics = ReviewStatistics(
                        totalReviews = totalCountResult.data,
                        movieReviews = movieCountResult.data,
                        tvReviews = tvCountResult.data,
                        averageRating = averageRating
                    )
                    emit(UserResult.Success(statistics))
                }
                else -> {
                    emit(
                        UserResult.Error(
                            UserError(
                                titleRes = R.string.error_title_generic,
                                subtitleRes = R.string.error_subtitle_generic
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_generic,
                        subtitleRes = R.string.error_subtitle_generic
                    )
                )
            )
        }
    }
}