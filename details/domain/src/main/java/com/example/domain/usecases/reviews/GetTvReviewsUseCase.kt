package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.repositories.ITvDetailsRepository

class GetTvReviewsUseCase(
    private val repository: ITvDetailsRepository
) : IGetTvReviewsUseCase {
    override suspend fun invoke(tvId: Int): ReviewResult<List<Review>> {
        return repository.getTvReviews(tvId)
    }
}
