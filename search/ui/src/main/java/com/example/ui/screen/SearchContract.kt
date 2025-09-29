package com.example.ui.screen

import com.example.core_domain.MediaType
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.models.SearchError
import com.example.domain.models.SearchItem

class SearchContract {
    sealed class Events : UiEvent {
        data class SearchQueryChanged(val query: String) : Events()
        data class MediaItemClicked(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data object ClearSearch : Events()
        data object Retry : Events()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Error(val error: SearchError) : State()
        data class Success(val searchResults: List<SearchResultItem>) : State()
        data object EmptyResults : State()
    }

    sealed class Effects : UiEffect {
        data class NavigateToDetails(val mediaItemId: Int, val mediaType: MediaType) : Effects()
    }

    // Wrapper class to add media type information to search results
    data class SearchResultItem(
        val searchItem: SearchItem,
        val mediaType: MediaType
    )
}
