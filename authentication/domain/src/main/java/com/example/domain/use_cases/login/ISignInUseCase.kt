package com.example.domain.use_cases.login

import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainUser

interface ISignInUseCase {
    suspend operator fun invoke(request: DomainSignInRequest): DomainAuthResult<DomainUser>
}