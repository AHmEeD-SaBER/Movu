package com.example.data.models

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val exceptionRes: Int) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}