package com.example.domain.usecases.reviews

import com.example.domain.ReviewResult
import com.example.domain.ReviewItem

interface IAddTvReviewUseCase {
    suspend operator fun invoke(reviewItem: ReviewItem): ReviewResult<Unit>
}

