package com.example.data.utils

import com.example.user_preferences.models.User
import com.example.user_preferences.models.SignUpRequest
import com.example.user_preferences.models.SignInRequest
import com.example.user_preferences.models.AuthResult
import com.example.domain.models.DomainUser
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainAuthResult

fun User.toDomain(): DomainUser {
    return DomainUser(
        uid = this.uid,
        email = this.email,
        username = this.username,
        createdAt = this.createdAt
    )
}

fun SignUpRequest.toDomain(): DomainSignUpRequest {
    return DomainSignUpRequest(
        email = this.email,
        password = this.password,
        username = this.username
    )
}

fun SignInRequest.toDomain(): DomainSignInRequest {
    return DomainSignInRequest(
        email = this.email,
        password = this.password
    )
}

fun <T, R> AuthResult<T>.toDomain(transform: (T) -> R): DomainAuthResult<R> {
    return when (this) {
        is AuthResult.Success -> DomainAuthResult.Success(transform(this.data))
        is AuthResult.Error<*> -> DomainAuthResult.Error(this.exception as Int)
        is AuthResult.Loading -> DomainAuthResult.Loading
    }
}

fun AuthResult<User>.toDomainUser(): DomainAuthResult<DomainUser> {
    return when (this) {
        is AuthResult.Success -> DomainAuthResult.Success(this.data.toDomain())
        is AuthResult.Error<*> -> DomainAuthResult.Error(this.exception as Int)
        is AuthResult.Loading -> DomainAuthResult.Loading
    }
}

fun DomainSignUpRequest.toData(): SignUpRequest {
    return SignUpRequest(
        email = this.email,
        password = this.password,
        username = this.username
    )
}

fun DomainSignInRequest.toData(): SignInRequest {
    return SignInRequest(
        email = this.email,
        password = this.password
    )
}
