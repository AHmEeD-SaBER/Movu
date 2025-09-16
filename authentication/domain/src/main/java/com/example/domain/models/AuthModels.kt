package com.example.domain.models

data class DomainUser(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class DomainSignUpRequest(
    val email: String,
    val password: String,
    val username: String
)

data class DomainSignInRequest(
    val email: String,
    val password: String
)

sealed class DomainAuthResult<out T> {
    data class Success<T>(val data: T) : DomainAuthResult<T>()
    data class Error(val exceptionRes: Int) : DomainAuthResult<Nothing>()
}

