package com.example.domain.repositories

import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainUser


interface IAuthRepository {
    suspend fun signUp(request: DomainSignUpRequest): DomainAuthResult<DomainUser>
    suspend fun signIn(request: DomainSignInRequest): DomainAuthResult<DomainUser>
}