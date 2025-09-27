package com.example.ui

import com.example.core_domain.MediaType
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.models.WatchlistError
import com.example.domain.models.WatchlistItem

class WatchlistContract {
    sealed class Events : UiEvent {
        data class MediaItemClicked(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data class MediaItemLongClicked(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data class MediaItemSelectedToggle(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data object Retry : Events()
        data object LoadData : Events()
        data object EnterSelectionMode : Events()
        data object ExitSelectionMode : Events()
        data object DeleteSelectedItems : Events()
        data object SelectAllItems : Events()
        data object DeselectAllItems : Events()
    }

    sealed class State : UiState {
        data object Loading : State()
        data class Error(val error: WatchlistError) : State()
        data class Success(
            val movies: List<WatchlistItem>,
            val tvShows: List<WatchlistItem>,
            val isSelectionMode: Boolean = false,
            val selectedMovieIds: Set<Int> = emptySet(),
            val selectedTvShowIds: Set<Int> = emptySet(),
            val isDeleting: Boolean = false
        ) : State()
        data object Idle : State()
    }

    sealed class Effects : UiEffect {
        data class NavigateToDetails(val mediaItemId: Int, val mediaType: MediaType) : Effects()
        data object ShowDeleteSuccess : Effects()
        data class ShowDeleteError(val error: WatchlistError) : Effects()
    }
}