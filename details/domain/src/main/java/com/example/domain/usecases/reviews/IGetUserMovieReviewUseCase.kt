package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review

interface IGetUserMovieReviewUseCase {
    suspend operator fun invoke(movieId: Int): ReviewResult<Review?>
}

