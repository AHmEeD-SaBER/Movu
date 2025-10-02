package com.example.data.utils

import com.example.user_preferences.models.WatchlistResult
import com.example.user_preferences.models.User as AuthUser
import com.example.domain.models.ProfileDomainUser as ProfileDomainUser

fun AuthUser.toProfileDomainUser(): ProfileDomainUser {
    return ProfileDomainUser(
        id = this.uid,
        email = this.email,
        username = this.username,
        createdAt = this.createdAt
    )
}

fun ProfileDomainUser.toAuthUser(): AuthUser {
    return AuthUser(
        uid = this.id,
        email = this.email,
        username = this.username,
        createdAt = this.createdAt
    )
}

fun WatchlistResult<Pair<Int, Int>>.toDomainWatchlistResult(): com.example.domain.models.UserResult<Pair<Int, Int>> {
    return when (this) {
        is WatchlistResult.Success -> com.example.domain.models.UserResult.Success(this.data)
        is WatchlistResult.Error -> com.example.domain.models.UserResult.Error(
            com.example.domain.models.UserError(
                titleRes = this.error.titleRes,
                subtitleRes = this.error.subtitleRes
            )
        )
    }
}



