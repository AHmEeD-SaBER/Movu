package com.example.domain.models

data class User(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String
)

data class SignInRequest(
    val email: String,
    val password: String
)

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error<T>(val exception: T) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}

