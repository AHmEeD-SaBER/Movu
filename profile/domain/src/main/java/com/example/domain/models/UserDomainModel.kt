package com.example.domain.models

import com.example.core_domain.CustomError

data class ProfileDomainUser(
    val id: String,
    val email: String,
    val username: String,
    val createdAt: Long
)

sealed class UserResult<out T> {
    data class Success<T>(val data: T) : UserResult<T>()
    data class Error(val error: UserError) : UserResult<Nothing>()
}

data class UserError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)