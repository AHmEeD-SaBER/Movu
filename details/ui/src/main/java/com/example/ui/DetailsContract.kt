package com.example.ui

import com.example.core_domain.MediaType
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.DetailsError
import com.example.domain.MediaDetails

class DetailsContract {
    sealed class Events : UiEvent {
        data object BackButtonClicked : Events()
        data object Retry : Events()
        data class LoadData(val mediaItemId: Int, val mediaType: MediaType) : Events()

    }

    sealed class State : UiState {
        data object Loading : State()
        data class Error(val error: DetailsError) : State()
        data class Success(val details: MediaDetails) : State()
        data object Idle : State()
    }

    sealed class Effects : UiEffect {
        data object NavigateBack : Effects()
    }
}