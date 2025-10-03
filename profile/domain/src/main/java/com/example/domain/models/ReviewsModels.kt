package com.example.domain.models

data class ReviewStatistics(
    val totalReviews: Int,
    val movieReviews: Int,
    val tvReviews: Int,
    val averageRating: Float
)
