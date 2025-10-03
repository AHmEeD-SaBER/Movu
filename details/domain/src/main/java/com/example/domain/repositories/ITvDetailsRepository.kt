package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.ReviewItem
import kotlinx.coroutines.flow.Flow

interface ITvDetailsRepository {
    fun getTvDetails(tvId: Int): Flow<DetailsResult<Tv>>

    // Watchlist operations
    suspend fun addTvToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit>
    suspend fun removeTvFromWatchlist(tvId: Int): WatchlistResult<Unit>
    suspend fun isTvInWatchlist(tvId: Int): WatchlistResult<Boolean>

    // Review operations
    suspend fun addTvReview(reviewItem: ReviewItem): ReviewResult<Unit>
    suspend fun updateTvReview(reviewItem: ReviewItem): ReviewResult<Unit>
    suspend fun deleteTvReview(tvId: Int): ReviewResult<Unit>
    suspend fun getTvReviews(tvId: Int): ReviewResult<List<Review>>
    suspend fun getUserTvReview(tvId: Int): ReviewResult<Review?>
}
