package com.example.ui

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState

class SplashContract {

    sealed class Event : UiEvent {
        object CheckAuthStatus : Event()
    }

    data class State(
        val isLoading: Boolean = true
    ) : UiState

    sealed class Effect : UiEffect {
        object NavigateToAuth : Effect()
        object NavigateToHome : Effect()
    }
}