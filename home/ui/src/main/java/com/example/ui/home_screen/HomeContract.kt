package com.example.ui.home_screen

import com.example.core_domain.MediaType
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.models.MediaError
import com.example.domain.models.MediaItem

class HomeContract {
    sealed class Events : UiEvent {
        data class MediaItemClicked(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data object Retry : Events()
        data object LoadData : Events()

    }

    sealed class State : UiState {
        data object Loading : State()
        data class Error(val error: MediaError) : State()
        data class Success(val movies: List<MediaItem>, val tvShows: List<MediaItem>, val randomMovie: MediaItem) : State()
        data object Idle : State()
    }

    sealed class Effects : UiEffect {
        data class NavigateToDetail(val mediaItemId: Int, val mediaType: MediaType) : Effects()
    }
}