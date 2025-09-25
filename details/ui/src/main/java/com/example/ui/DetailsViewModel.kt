package com.example.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core_domain.MediaType
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.base.Routes
import com.example.domain.DetailsResult
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.domain.repositories.ITvDetailsRepository
import kotlinx.coroutines.launch
import androidx.navigation.toRoute

class DetailsViewModel(
    private val getMediaDetailsUseCase: com.example.domain.usecases.mediadetails.IGetMediaDetailsUseCase,
    private val movieDetailsRepository: IMovieDetailsRepository,
    private val tvDetailsRepository: ITvDetailsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailsContract.Events, DetailsContract.State, DetailsContract.Effects>() {

    private val detailsRoute = savedStateHandle.toRoute<Routes.Details>()
    private val mediaItemId = detailsRoute.mediaId
    private val mediaType = detailsRoute.mediaType

    init {
        loadData()
    }

    override fun setInitialState(): DetailsContract.State {
        return DetailsContract.State.Idle
    }

    override fun handleEvent(event: DetailsContract.Events) {
        when(event){
            DetailsContract.Events.BackButtonClicked -> onBackButtonClicked()
            is DetailsContract.Events.LoadData -> loadData(event.mediaItemId, event.mediaType)
            DetailsContract.Events.Retry -> onRetry()
            is DetailsContract.Events.WatchTrailer -> onWatchTrailer(event.trailerLink)
            DetailsContract.Events.ToggleWatchlist -> onToggleWatchlist()
            DetailsContract.Events.ConfirmRemoveFromWatchlist -> onConfirmRemoveFromWatchlist()
            DetailsContract.Events.DismissRemoveConfirmation -> onDismissRemoveConfirmation()
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            setEffect { DetailsContract.Effects.NavigateBack }
        }
    }

    private fun onRetry() {
        loadData()
    }

    private fun loadData(itemId: Int = mediaItemId, type: MediaType = mediaType) {
        viewModelScope.launch {
            setState { DetailsContract.State.Loading }

            try {
                val detailsFlow = getMediaDetailsUseCase(itemId, type)

                detailsFlow.collect { result ->
                    when (result) {
                        is DetailsResult.Success -> {
                            // Check if item is in watchlist
                            checkWatchlistStatus(itemId, type, result.data)
                        }
                        is DetailsResult.Error -> {
                            setState { DetailsContract.State.Error(result.error) }
                        }
                    }
                }
            } catch (e: Exception) {
                setState {
                    DetailsContract.State.Error(
                        com.example.domain.DetailsError(
                            titleRes = android.R.string.dialog_alert_title,
                            subtitleRes = android.R.string.unknownName
                        )
                    )
                }
            }
        }
    }

    private suspend fun checkWatchlistStatus(itemId: Int, type: MediaType, details: com.example.domain.MediaDetails) {
        val watchlistResult = when (type) {
            MediaType.MOVIE -> movieDetailsRepository.isMovieInWatchlist(itemId)
            MediaType.TV_SHOW -> tvDetailsRepository.isTvInWatchlist(itemId)
        }

        when (watchlistResult) {
            is WatchlistResult.Success -> {
                setState {
                    DetailsContract.State.Success(
                        details = details,
                        isInWatchlist = watchlistResult.data,
                        watchlistLoading = false
                    )
                }
            }
            is WatchlistResult.Error -> {
                setState {
                    DetailsContract.State.Success(
                        details = details,
                        isInWatchlist = false,
                        watchlistLoading = false
                    )
                }
            }
        }
    }

    private fun onToggleWatchlist() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        if (currentState.isInWatchlist) {
            // Show confirmation dialog for removal
            setState {
                currentState.copy(showRemoveConfirmation = true)
            }
        } else {
            // Add to watchlist directly
            performWatchlistOperation(currentState, isRemoving = false)
        }
    }

    private fun onConfirmRemoveFromWatchlist() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        // First, dismiss the dialog and start the removal operation
        performWatchlistOperation(currentState.copy(showRemoveConfirmation = false), isRemoving = true)
    }

    private fun onDismissRemoveConfirmation() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        setState {
            currentState.copy(showRemoveConfirmation = false)
        }
    }

    private fun performWatchlistOperation(currentState: DetailsContract.State.Success, isRemoving: Boolean) {
        viewModelScope.launch {
            // Show loading state while keeping dialog dismissed
            setState {
                currentState.copy(
                    watchlistLoading = true,
                    showRemoveConfirmation = false
                )
            }

            val watchlistItem = WatchlistItem(
                id = mediaItemId,
                title = currentState.details.title,
                rating = currentState.details.rating,
                image = currentState.details.image
            )

            val result = if (isRemoving) {
                // Remove from watchlist
                when (mediaType) {
                    MediaType.MOVIE -> movieDetailsRepository.removeMovieFromWatchlist(mediaItemId)
                    MediaType.TV_SHOW -> tvDetailsRepository.removeTvFromWatchlist(mediaItemId)
                }
            } else {
                // Add to watchlist
                when (mediaType) {
                    MediaType.MOVIE -> movieDetailsRepository.addMovieToWatchlist(watchlistItem)
                    MediaType.TV_SHOW -> tvDetailsRepository.addTvToWatchlist(watchlistItem)
                }
            }

            when (result) {
                is WatchlistResult.Success -> {
                    setState {
                        currentState.copy(
                            isInWatchlist = !currentState.isInWatchlist,
                            watchlistLoading = false,
                            showRemoveConfirmation = false
                        )
                    }
                }
                is WatchlistResult.Error -> {
                    setState {
                        currentState.copy(
                            watchlistLoading = false,
                            showRemoveConfirmation = false
                        )
                    }
                    setEffect { DetailsContract.Effects.ShowWatchlistError(result.error) }
                }
            }
        }
    }

    private fun onWatchTrailer(trailerLink: String) {
        viewModelScope.launch {
            setEffect { DetailsContract.Effects.OpenTrailer(trailerLink) }
        }
    }

}