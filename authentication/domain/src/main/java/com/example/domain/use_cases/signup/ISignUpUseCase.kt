package com.example.domain.use_cases.signup

import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainUser

interface ISignUpUseCase {
    suspend operator fun invoke(request: DomainSignUpRequest): DomainAuthResult<DomainUser>
}