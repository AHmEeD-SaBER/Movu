package com.example.domain.use_cases.get_user

import com.example.domain.models.UserResult
import kotlinx.coroutines.flow.Flow

interface IGetWatchlistCount {
    suspend operator fun invoke(): Flow<UserResult<Pair<Int, Int>>>
}