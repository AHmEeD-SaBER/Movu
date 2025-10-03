package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.Review

interface IGetTvReviewsUseCase {
    suspend operator fun invoke(tvId: Int): ReviewResult<List<Review>>
}

