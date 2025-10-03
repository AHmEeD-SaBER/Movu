package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Movie
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.ReviewItem
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<DetailsResult<Movie>>

    // Watchlist operations
    suspend fun addMovieToWatchlist(watchlistItem: WatchlistItem): WatchlistResult<Unit>
    suspend fun removeMovieFromWatchlist(movieId: Int): WatchlistResult<Unit>
    suspend fun isMovieInWatchlist(movieId: Int): WatchlistResult<Boolean>

    // Review operations
    suspend fun addMovieReview(reviewItem: ReviewItem): ReviewResult<Unit>
    suspend fun updateMovieReview(reviewItem: ReviewItem): ReviewResult<Unit>
    suspend fun deleteMovieReview(movieId: Int): ReviewResult<Unit>
    suspend fun getMovieReviews(movieId: Int): ReviewResult<List<Review>>
    suspend fun getUserMovieReview(movieId: Int): ReviewResult<Review?>
}
