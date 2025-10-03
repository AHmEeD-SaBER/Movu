package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.repositories.IMovieDetailsRepository

class GetUserMovieReviewUseCase(
    private val repository: IMovieDetailsRepository
) : IGetUserMovieReviewUseCase {
    override suspend fun invoke(movieId: Int): ReviewResult<Review?> {
        return repository.getUserMovieReview(movieId)
    }
}
