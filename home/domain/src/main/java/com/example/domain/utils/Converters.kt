package com.example.domain.utils

import com.example.domain.models.*

// Note: The converters will need to be implemented in the data layer
// where they have access to the data models. This file serves as a template.

// Extension functions to convert MediaResult to Flow<Result<T>>
fun <T> MediaResult<T>.toResult(): Result<T> {
    return when (this) {
        is MediaResult.Success -> Result.success(data)
        is MediaResult.Error -> Result.failure(Exception("Error code: $exceptionRes"))
    }
}

// Extension function to convert Result to MediaResult
fun <T> Result<T>.toMediaResult(): MediaResult<T> {
    return if (isSuccess) {
        MediaResult.Success(getOrThrow())
    } else {
        MediaResult.Error(-1) // Generic error code, can be customized
    }
}
