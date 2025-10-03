package com.example.ui

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.models.ProfileDomainUser
import com.example.domain.models.UserError
import com.example.domain.models.ReviewStatistics

class ProfileContract {
    sealed class Events : UiEvent {
        data object LoadUserData : Events()
        data object Retry : Events()
        data object LogoutClicked : Events()
        data object ConfirmLogout : Events()
        data object DismissLogoutConfirmation : Events()
    }

    sealed class State : UiState {
        data object Loading : State()
        data class Error(val error: UserError) : State()
        data class Success(
            val user: ProfileDomainUser,
            val watchlistCounts: Pair<Int, Int>, // (moviesCount, tvShowsCount)
            val reviewStatistics: ReviewStatistics? = null,
            val showLogoutConfirmation: Boolean = false
        ) : State()
        data object Idle : State()
    }

    sealed class Effects : UiEffect {
        data object NavigateToAuth : Effects()
        data class ShowError(val error: UserError) : Effects()
    }
}
