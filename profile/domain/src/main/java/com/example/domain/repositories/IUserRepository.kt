package com.example.domain.repositories

import com.example.domain.models.ProfileDomainUser
import com.example.domain.models.UserResult
import com.example.domain.models.ReviewStatistics
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun getCurrentUser(): Flow<UserResult<ProfileDomainUser>>
    suspend fun getUserWatchListCount() : Flow<UserResult<Pair<Int, Int>>>
    suspend fun logout(): Flow<UserResult<Unit>>
    suspend fun getUserReviewStatistics(): Flow<UserResult<ReviewStatistics>>
}