package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.ReviewItem
import com.example.domain.repositories.IMovieDetailsRepository

class AddMovieReviewUseCase(
    private val repository: IMovieDetailsRepository
) : IAddMovieReviewUseCase {
    override suspend fun invoke(reviewItem: ReviewItem): ReviewResult<Unit> {
        return repository.addMovieReview(reviewItem)
    }
}
