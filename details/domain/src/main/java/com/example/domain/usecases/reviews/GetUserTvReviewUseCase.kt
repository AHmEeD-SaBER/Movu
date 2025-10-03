package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.repositories.ITvDetailsRepository

class GetUserTvReviewUseCase(
    private val repository: ITvDetailsRepository
) : IGetUserTvReviewUseCase {
    override suspend fun invoke(tvId: Int): ReviewResult<Review?> {
        return repository.getUserTvReview(tvId)
    }
}
