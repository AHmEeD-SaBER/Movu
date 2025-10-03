package com.example.domain.usecases.reviews

import com.example.domain.models.UserResult
import com.example.domain.models.ReviewStatistics
import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow

class GetUserReviewStatisticsUseCase(
    private val repository: IUserRepository
) : IGetUserReviewStatisticsUseCase {
    override suspend fun invoke(): Flow<UserResult<ReviewStatistics>> {
        return repository.getUserReviewStatistics()
    }
}
