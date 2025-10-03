package com.example.domain.usecases.reviews

import com.example.domain.models.UserResult
import com.example.domain.models.ReviewStatistics
import kotlinx.coroutines.flow.Flow

interface IGetUserReviewStatisticsUseCase {
    suspend operator fun invoke(): Flow<UserResult<ReviewStatistics>>
}
