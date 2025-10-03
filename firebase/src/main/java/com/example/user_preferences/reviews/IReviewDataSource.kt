package com.example.user_preferences.reviews

import com.example.user_preferences.models.FirebaseReview
import com.example.user_preferences.models.ReviewResult

interface IReviewDataSource {

    // Add reviews
    suspend fun addMovieReview(mediaId: Int, rating: Float, reviewText: String): ReviewResult<Unit>
    suspend fun addTvShowReview(mediaId: Int, rating: Float, reviewText: String): ReviewResult<Unit>

    // Update reviews
    suspend fun updateMovieReview(mediaId: Int, rating: Float, reviewText: String): ReviewResult<Unit>
    suspend fun updateTvShowReview(mediaId: Int, rating: Float, reviewText: String): ReviewResult<Unit>

    // Delete reviews
    suspend fun deleteMovieReview(mediaId: Int): ReviewResult<Unit>
    suspend fun deleteTvShowReview(mediaId: Int): ReviewResult<Unit>

    // Get all reviews for a specific movie or TV show
    suspend fun getMovieReviews(mediaId: Int): ReviewResult<List<FirebaseReview>>
    suspend fun getTvShowReviews(mediaId: Int): ReviewResult<List<FirebaseReview>>

    // Get user's own review for a specific movie or TV show
    suspend fun getUserMovieReview(mediaId: Int): ReviewResult<FirebaseReview?>
    suspend fun getUserTvShowReview(mediaId: Int): ReviewResult<FirebaseReview?>

    // Get count of reviews a user has made
    suspend fun getUserReviewCount(): ReviewResult<Int>
    suspend fun getUserMovieReviewCount(): ReviewResult<Int>
    suspend fun getUserTvShowReviewCount(): ReviewResult<Int>

    // Get all reviews by the current user
    suspend fun getAllUserReviews(): ReviewResult<List<FirebaseReview>>
}

