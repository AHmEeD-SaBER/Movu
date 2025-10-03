package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.ReviewItem
import com.example.domain.repositories.ITvDetailsRepository

class AddTvReviewUseCase(
    private val repository: ITvDetailsRepository
) : IAddTvReviewUseCase {
    override suspend fun invoke(reviewItem: ReviewItem): ReviewResult<Unit> {
        return repository.addTvReview(reviewItem)
    }
}
