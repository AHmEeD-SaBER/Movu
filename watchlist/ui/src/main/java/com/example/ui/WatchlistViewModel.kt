package com.example.ui

import androidx.lifecycle.viewModelScope
import com.example.core_domain.MediaType
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.WatchlistResult
import com.example.domain.usecases.removemovies.IRemoveMoviesFromWatchlist
import com.example.domain.usecases.removetvshows.IRemoveTvShowsFromWatchlist
import com.example.domain.usecases.watchlistmovies.IGetWatchlistMovies
import com.example.domain.usecases.watchlisttvshows.IGetWatchlistTvShows
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val movies: IGetWatchlistMovies,
    private val tvShows: IGetWatchlistTvShows,
    private val removeMovies: IRemoveMoviesFromWatchlist,
    private val removeTvShows: IRemoveTvShowsFromWatchlist
) : BaseViewModel<WatchlistContract.Events, WatchlistContract.State, WatchlistContract.Effects>() {

    override fun setInitialState(): WatchlistContract.State {
        return WatchlistContract.State.Idle
    }


    override fun handleEvent(event: WatchlistContract.Events) {
        when (event) {
            is WatchlistContract.Events.MediaItemClicked -> {
                handleMediaItemClicked(event.mediaItemId, event.mediaType)
            }
            is WatchlistContract.Events.MediaItemLongClicked -> {
                handleMediaItemLongClicked(event.mediaItemId, event.mediaType)
            }
            is WatchlistContract.Events.MediaItemSelectedToggle -> {
                handleMediaItemSelectedToggle(event.mediaItemId, event.mediaType)
            }
            WatchlistContract.Events.Retry -> {
                handleOnRetry()
            }
            WatchlistContract.Events.LoadData -> {
                handleLoadData()
            }
            WatchlistContract.Events.EnterSelectionMode -> {
                handleEnterSelectionMode()
            }
            WatchlistContract.Events.ExitSelectionMode -> {
                handleExitSelectionMode()
            }
            WatchlistContract.Events.DeleteSelectedItems -> {
                handleDeleteSelectedItems()
            }
            WatchlistContract.Events.SelectAllItems -> {
                handleSelectAllItems()
            }
            WatchlistContract.Events.DeselectAllItems -> {
                handleDeselectAllItems()
            }
        }
    }

    private fun handleMediaItemClicked(mediaItemId: Int, mediaType: MediaType) {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            handleMediaItemSelectedToggle(mediaItemId, mediaType)
        } else {
            setEffect { WatchlistContract.Effects.NavigateToDetails(mediaItemId, mediaType) }
        }
    }

    private fun handleMediaItemLongClicked(mediaItemId: Int, mediaType: MediaType) {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success) {
            if (!currentState.isSelectionMode) {
                val newSelectedMovieIds = if (mediaType == MediaType.MOVIE) {
                    setOf(mediaItemId)
                } else {
                    emptySet()
                }
                val newSelectedTvShowIds = if (mediaType == MediaType.TV_SHOW) {
                    setOf(mediaItemId)
                } else {
                    emptySet()
                }

                setState {
                    currentState.copy(
                        isSelectionMode = true,
                        selectedMovieIds = newSelectedMovieIds,
                        selectedTvShowIds = newSelectedTvShowIds
                    )
                }
            } else {
                handleMediaItemSelectedToggle(mediaItemId, mediaType)
            }
        }
    }

    private fun handleMediaItemSelectedToggle(mediaItemId: Int, mediaType: MediaType) {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            when (mediaType) {
                MediaType.MOVIE -> {
                    val newSelectedIds = if (currentState.selectedMovieIds.contains(mediaItemId)) {
                        currentState.selectedMovieIds - mediaItemId
                    } else {
                        currentState.selectedMovieIds + mediaItemId
                    }
                    setState {
                        currentState.copy(selectedMovieIds = newSelectedIds)
                    }
                }
                MediaType.TV_SHOW -> {
                    val newSelectedIds = if (currentState.selectedTvShowIds.contains(mediaItemId)) {
                        currentState.selectedTvShowIds - mediaItemId
                    } else {
                        currentState.selectedTvShowIds + mediaItemId
                    }
                    setState {
                        currentState.copy(selectedTvShowIds = newSelectedIds)
                    }
                }
            }

            val updatedState = uiState.value as WatchlistContract.State.Success
            if (updatedState.selectedMovieIds.isEmpty() && updatedState.selectedTvShowIds.isEmpty()) {
                setState {
                    updatedState.copy(isSelectionMode = false)
                }
            }
        }
    }

    private fun handleEnterSelectionMode() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success) {
            setState {
                currentState.copy(
                    isSelectionMode = true,
                    selectedMovieIds = emptySet(),
                    selectedTvShowIds = emptySet()
                )
            }
        }
    }

    private fun handleExitSelectionMode() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success) {
            setState {
                currentState.copy(
                    isSelectionMode = false,
                    selectedMovieIds = emptySet(),
                    selectedTvShowIds = emptySet()
                )
            }
        }
    }

    private fun handleSelectAllItems() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            setState {
                currentState.copy(
                    selectedMovieIds = currentState.movies.map { it.id }.toSet(),
                    selectedTvShowIds = currentState.tvShows.map { it.id }.toSet()
                )
            }
        }
    }

    private fun handleDeselectAllItems() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            setState {
                currentState.copy(
                    selectedMovieIds = emptySet(),
                    selectedTvShowIds = emptySet()
                )
            }
        }
    }

    private fun handleDeleteSelectedItems() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            val totalSelectedCount = currentState.selectedMovieIds.size + currentState.selectedTvShowIds.size

            if (totalSelectedCount > 0) {
                // Actually perform the deletion when this event is received
                confirmDeleteSelectedItems()
            }
        }
    }

    fun confirmDeleteSelectedItems() {
        val currentState = uiState.value
        if (currentState is WatchlistContract.State.Success && currentState.isSelectionMode) {
            setState {
                currentState.copy(isDeleting = true)
            }

            viewModelScope.launch {
                val selectedMovieIds = currentState.selectedMovieIds.toList()
                val selectedTvShowIds = currentState.selectedTvShowIds.toList()

                var hasError = false
                var errorResult: WatchlistResult.Error? = null

                if (selectedMovieIds.isNotEmpty()) {
                    when (val result = removeMovies(selectedMovieIds)) {
                        is WatchlistResult.Error -> {
                            hasError = true
                            errorResult = result
                        }
                        is WatchlistResult.Success -> {
                        }
                    }
                }

                if (!hasError && selectedTvShowIds.isNotEmpty()) {
                    when (val result = removeTvShows(selectedTvShowIds)) {
                        is WatchlistResult.Error -> {
                            hasError = true
                            errorResult = result
                        }
                        is WatchlistResult.Success -> {
                        }
                    }
                }

                if (hasError && errorResult != null) {
                    setEffect {
                        WatchlistContract.Effects.ShowDeleteError(errorResult.error)
                    }
                } else {
                    setEffect {
                        WatchlistContract.Effects.ShowDeleteSuccess
                    }
                }

                handleExitSelectionMode()
                handleLoadData()
            }
        }
    }

    private fun handleOnRetry() {
        handleLoadData()
    }

    private fun handleLoadData() {
        setState { WatchlistContract.State.Loading }

        viewModelScope.launch {
            try {
                combine(
                    movies(),
                    tvShows()
                ) { moviesResult, tvShowsResult ->
                    when {
                        moviesResult is WatchlistResult.Error -> {
                            WatchlistContract.State.Error(moviesResult.error)
                        }
                        tvShowsResult is WatchlistResult.Error -> {
                            WatchlistContract.State.Error(tvShowsResult.error)
                        }
                        moviesResult is WatchlistResult.Success && tvShowsResult is WatchlistResult.Success -> {
                            WatchlistContract.State.Success(
                                movies = moviesResult.data.items,
                                tvShows = tvShowsResult.data.items
                            )
                        }
                        else -> {
                            WatchlistContract.State.Loading
                        }
                    }
                }.collect { newState ->
                    setState { newState }
                }
            } catch (e: Exception) {
                setState {
                    WatchlistContract.State.Error(
                        com.example.domain.models.WatchlistError(
                            titleRes = R.string.error_title_unknown,
                            subtitleRes = R.string.error_subtitle_unknown
                        )
                    )
                }
            }
        }
    }
}