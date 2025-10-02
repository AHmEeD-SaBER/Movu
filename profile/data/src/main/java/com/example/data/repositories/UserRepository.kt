package com.example.data.repositories

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.ProfileDomainUser
import com.example.domain.models.UserResult
import com.example.domain.models.UserError
import com.example.domain.repositories.IUserRepository
import com.example.user_preferences.auth.IAuthDataSource
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.example.data.utils.toProfileDomainUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.R
import com.example.data.utils.toDomainWatchlistResult

class UserRepository(
    private val authDataSource: IAuthDataSource,
    private val watchlistDataSource: IWatchlistDataSource,
    private val networkMonitor: INetworkMonitor
) : IUserRepository {

    override suspend fun getCurrentUser(): Flow<UserResult<ProfileDomainUser>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        try {
            val currentUser = authDataSource.getCurrentUser()
            if (currentUser != null) {
                emit(UserResult.Success(currentUser.toProfileDomainUser()))
            } else {
                emit(
                    UserResult.Error(
                        UserError(
                            titleRes = R.string.error_title_auth,
                            subtitleRes = R.string.error_subtitle_auth
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_generic,
                        subtitleRes = R.string.error_subtitle_generic
                    )
                )
            )
        }
    }

    override suspend fun getUserWatchListCount(): Flow<UserResult<Pair<Int, Int>>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_network,
                        subtitleRes = R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }

        val result = watchlistDataSource.getUserTotalWatchlistCount()
        emit(result.toDomainWatchlistResult())
    }

    override suspend fun logout(): Flow<UserResult<Unit>> = flow {
        try {
            authDataSource.signOut()
            emit(UserResult.Success(Unit))
        } catch (e: Exception) {
            emit(
                UserResult.Error(
                    UserError(
                        titleRes = R.string.error_title_generic,
                        subtitleRes = R.string.error_subtitle_generic
                    )
                )
            )
        }
    }
}