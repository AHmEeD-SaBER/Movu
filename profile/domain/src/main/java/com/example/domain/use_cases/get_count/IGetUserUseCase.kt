package com.example.domain.use_cases.get_count

import com.example.domain.models.ProfileDomainUser
import com.example.domain.models.UserResult
import kotlinx.coroutines.flow.Flow

interface IGetUserUseCase {
    suspend operator fun invoke(): Flow<UserResult<ProfileDomainUser>>
}