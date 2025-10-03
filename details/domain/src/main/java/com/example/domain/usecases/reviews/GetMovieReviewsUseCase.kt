package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review
import com.example.domain.repositories.IMovieDetailsRepository

class GetMovieReviewsUseCase(
    private val repository: IMovieDetailsRepository
) : IGetMovieReviewsUseCase {
    override suspend fun invoke(movieId: Int): ReviewResult<List<Review>> {
        return repository.getMovieReviews(movieId)
    }
}
