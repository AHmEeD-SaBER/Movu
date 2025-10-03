package com.example.ui

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.UserResult
import com.example.domain.repositories.IUserRepository
import com.example.domain.usecases.logout.ILogoutUseCase
import com.example.domain.usecases.reviews.IGetUserReviewStatisticsUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: IUserRepository,
    private val logoutUseCase: ILogoutUseCase,
    private val getUserReviewStatisticsUseCase: IGetUserReviewStatisticsUseCase
) : BaseViewModel<ProfileContract.Events, ProfileContract.State, ProfileContract.Effects>() {

    init {
        loadUserData()
    }

    override fun setInitialState(): ProfileContract.State {
        return ProfileContract.State.Idle
    }

    override fun handleEvent(event: ProfileContract.Events) {
        when (event) {
            ProfileContract.Events.LoadUserData -> loadUserData()
            ProfileContract.Events.Retry -> onRetry()
            ProfileContract.Events.LogoutClicked -> onLogoutClicked()
            ProfileContract.Events.ConfirmLogout -> onConfirmLogout()
            ProfileContract.Events.DismissLogoutConfirmation -> onDismissLogoutConfirmation()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            setState { ProfileContract.State.Loading }

            try {
                // Get current user
                userRepository.getCurrentUser().collect { userResult ->
                    when (userResult) {
                        is UserResult.Success -> {
                            val user = userResult.data
                            // Get watchlist counts
                            loadWatchlistCounts(user)
                        }
                        is UserResult.Error -> {
                            setState { ProfileContract.State.Error(userResult.error) }
                        }
                    }
                }
            } catch (e: Exception) {
                setState {
                    ProfileContract.State.Error(
                        com.example.domain.models.UserError(
                            titleRes = R.string.error_title_generic,
                            subtitleRes = R.string.error_subtitle_generic
                        )
                    )
                }
            }
        }
    }

    private fun loadWatchlistCounts(user: com.example.domain.models.ProfileDomainUser) {
        viewModelScope.launch {
            try {
                userRepository.getUserWatchListCount().collect { countsResult ->
                    when (countsResult) {
                        is UserResult.Success -> {
                            // Now load review statistics
                            loadReviewStatistics(user, countsResult.data)
                        }
                        is UserResult.Error -> {
                            setState {
                                ProfileContract.State.Success(
                                    user = user,
                                    watchlistCounts = Pair(0, 0),
                                    reviewStatistics = null
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                setState {
                    ProfileContract.State.Success(
                        user = user,
                        watchlistCounts = Pair(0, 0),
                        reviewStatistics = null
                    )
                }
            }
        }
    }

    private fun loadReviewStatistics(
        user: com.example.domain.models.ProfileDomainUser,
        watchlistCounts: Pair<Int, Int>
    ) {
        viewModelScope.launch {
            try {
                getUserReviewStatisticsUseCase().collect { reviewResult ->
                    when (reviewResult) {
                        is UserResult.Success -> {
                            setState {
                                ProfileContract.State.Success(
                                    user = user,
                                    watchlistCounts = watchlistCounts,
                                    reviewStatistics = reviewResult.data
                                )
                            }
                        }
                        is UserResult.Error -> {
                            // Show user data without review stats if review fetch fails
                            setState {
                                ProfileContract.State.Success(
                                    user = user,
                                    watchlistCounts = watchlistCounts,
                                    reviewStatistics = null
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                setState {
                    ProfileContract.State.Success(
                        user = user,
                        watchlistCounts = watchlistCounts,
                        reviewStatistics = null
                    )
                }
            }
        }
    }

    private fun onRetry() {
        loadUserData()
    }

    private fun onLogoutClicked() {
        val currentState = uiState.value
        if (currentState is ProfileContract.State.Success) {
            setState {
                currentState.copy(showLogoutConfirmation = true)
            }
        }
    }

    private fun onConfirmLogout() {
        val currentState = uiState.value
        if (currentState is ProfileContract.State.Success) {
            setState {
                currentState.copy(showLogoutConfirmation = false)
            }
        }

        setState { ProfileContract.State.Loading }

        viewModelScope.launch {
            try {
                logoutUseCase().collect { result ->
                    when (result) {
                        is UserResult.Success -> {
                            setEffect { ProfileContract.Effects.NavigateToAuth }
                        }
                        is UserResult.Error -> {
                            loadUserData()
                            setEffect {
                                ProfileContract.Effects.ShowError(result.error)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                loadUserData()
                setEffect {
                    ProfileContract.Effects.ShowError(
                        com.example.domain.models.UserError(
                            titleRes = R.string.error_title_generic,
                            subtitleRes = R.string.error_subtitle_generic
                        )
                    )
                }
            }
        }
    }

    private fun onDismissLogoutConfirmation() {
        val currentState = uiState.value
        if (currentState is ProfileContract.State.Success) {
            setState {
                currentState.copy(showLogoutConfirmation = false)
            }
        }
    }
}
