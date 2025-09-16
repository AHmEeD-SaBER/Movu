package com.example.data.utils

import com.example.user_preferences.models.User
import com.example.user_preferences.models.SignUpRequest
import com.example.user_preferences.models.SignInRequest
import com.example.user_preferences.models.AuthResult
import com.example.domain.models.DomainUser
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainAuthResult
import com.movu.authentication.data.R

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
        is AuthResult.Error<*> -> DomainAuthResult.Error(mapFirebaseExceptionToResId(this.exception))
    }
}

fun AuthResult<User>.toDomainUser(): DomainAuthResult<DomainUser> {
    return when (this) {
        is AuthResult.Success -> DomainAuthResult.Success(this.data.toDomain())
        is AuthResult.Error<*> -> DomainAuthResult.Error(mapFirebaseExceptionToResId(this.exception))
    }
}

private fun mapFirebaseExceptionToResId(exception: Any?): Int {
    return when (exception) {
        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> R.string.error_invalid_credentials
        is com.google.firebase.auth.FirebaseAuthInvalidUserException -> R.string.error_user_not_found
        is com.google.firebase.auth.FirebaseAuthUserCollisionException -> R.string.error_email_already_exists
        is com.google.firebase.auth.FirebaseAuthWeakPasswordException -> R.string.error_weak_password
        is Int -> exception
        else -> R.string.unexpected_error
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
