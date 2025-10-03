package com.example.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core_domain.MediaType
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.base.Routes
import com.example.domain.DetailsResult
import com.example.domain.WatchlistResult
import com.example.domain.WatchlistItem
import com.example.domain.ReviewResult
import com.example.domain.ReviewItem
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.domain.repositories.ITvDetailsRepository
import com.example.domain.usecases.reviews.*
import kotlinx.coroutines.launch
import androidx.navigation.toRoute

class DetailsViewModel(
    private val getMediaDetailsUseCase: com.example.domain.usecases.mediadetails.IGetMediaDetailsUseCase,
    private val movieDetailsRepository: IMovieDetailsRepository,
    private val tvDetailsRepository: ITvDetailsRepository,
    private val addMovieReviewUseCase: IAddMovieReviewUseCase,
    private val getMovieReviewsUseCase: IGetMovieReviewsUseCase,
    private val getUserMovieReviewUseCase: IGetUserMovieReviewUseCase,
    private val addTvReviewUseCase: IAddTvReviewUseCase,
    private val getTvReviewsUseCase: IGetTvReviewsUseCase,
    private val getUserTvReviewUseCase: IGetUserTvReviewUseCase,
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
            DetailsContract.Events.LoadReviews -> loadReviews()
            DetailsContract.Events.ShowAddReviewDialog -> onShowReviewDialog()
            DetailsContract.Events.DismissReviewDialog -> onDismissReviewDialog()
            is DetailsContract.Events.SubmitReview -> onSubmitReview(event.rating, event.reviewText)
            is DetailsContract.Events.UpdateReview -> onUpdateReview(event.rating, event.reviewText)
            DetailsContract.Events.DeleteReview -> onDeleteReview()
            DetailsContract.Events.ConfirmDeleteReview -> onConfirmDeleteReview()
            DetailsContract.Events.DismissDeleteConfirmation -> onDismissDeleteConfirmation()
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
                // Load reviews after details are loaded
                loadReviews()
                loadUserReview()
            }
            is WatchlistResult.Error -> {
                setState {
                    DetailsContract.State.Success(
                        details = details,
                        isInWatchlist = false,
                        watchlistLoading = false
                    )
                }
                // Still load reviews even if watchlist check fails
                loadReviews()
                loadUserReview()
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

    // Review Methods
    private fun loadReviews() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        viewModelScope.launch {
            setState { currentState.copy(reviewsLoading = true) }

            val result = when (mediaType) {
                MediaType.MOVIE -> getMovieReviewsUseCase(mediaItemId)
                MediaType.TV_SHOW -> getTvReviewsUseCase(mediaItemId)
            }

            when (result) {
                is ReviewResult.Success -> {
                    val latestState = uiState.value
                    if (latestState is DetailsContract.State.Success) {
                        setState {
                            latestState.copy(
                                reviews = result.data,
                                reviewsLoading = false
                            )
                        }
                    }
                }
                is ReviewResult.Error -> {
                    val latestState = uiState.value
                    if (latestState is DetailsContract.State.Success) {
                        setState {
                            latestState.copy(
                                reviews = emptyList(),
                                reviewsLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadUserReview() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        viewModelScope.launch {
            val result = when (mediaType) {
                MediaType.MOVIE -> getUserMovieReviewUseCase(mediaItemId)
                MediaType.TV_SHOW -> getUserTvReviewUseCase(mediaItemId)
            }

            when (result) {
                is ReviewResult.Success -> {
                    val latestState = uiState.value
                    if (latestState is DetailsContract.State.Success) {
                        setState {
                            latestState.copy(userReview = result.data)
                        }
                    }
                }
                is ReviewResult.Error -> {
                    // Silently fail - user just hasn't reviewed yet
                }
            }
        }
    }

    private fun onShowReviewDialog() {
        val currentState = uiState.value
        if (currentState is DetailsContract.State.Success) {
            setState {
                currentState.copy(showReviewDialog = true)
            }
        }
    }

    private fun onDismissReviewDialog() {
        val currentState = uiState.value
        if (currentState is DetailsContract.State.Success) {
            setState {
                currentState.copy(showReviewDialog = false)
            }
        }
    }

    private fun onSubmitReview(rating: Float, reviewText: String) {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        viewModelScope.launch {
            setState { currentState.copy(reviewSubmitting = true) }

            val reviewItem = ReviewItem(
                mediaId = mediaItemId,
                rating = rating,
                reviewText = reviewText
            )

            val result = when (mediaType) {
                MediaType.MOVIE -> addMovieReviewUseCase(reviewItem)
                MediaType.TV_SHOW -> addTvReviewUseCase(reviewItem)
            }

            when (result) {
                is ReviewResult.Success -> {
                    setState {
                        currentState.copy(
                            reviewSubmitting = false,
                            showReviewDialog = false
                        )
                    }
                    setEffect { DetailsContract.Effects.ReviewSubmittedSuccessfully }
                    // Reload reviews and user review
                    loadReviews()
                    loadUserReview()
                }
                is ReviewResult.Error -> {
                    setState {
                        currentState.copy(reviewSubmitting = false)
                    }
                    setEffect { DetailsContract.Effects.ShowReviewError(result.error) }
                }
            }
        }
    }

    private fun onUpdateReview(rating: Float, reviewText: String) {
        // Update uses the same logic as submit (Firebase set() overwrites)
        onSubmitReview(rating, reviewText)
    }

    private fun onDeleteReview() {
        val currentState = uiState.value
        if (currentState is DetailsContract.State.Success) {
            setState {
                currentState.copy(showDeleteReviewConfirmation = true)
            }
        }
    }

    private fun onConfirmDeleteReview() {
        val currentState = uiState.value
        if (currentState !is DetailsContract.State.Success) return

        viewModelScope.launch {
            setState {
                currentState.copy(
                    showDeleteReviewConfirmation = false,
                    reviewSubmitting = true
                )
            }

            val result = when (mediaType) {
                MediaType.MOVIE -> movieDetailsRepository.deleteMovieReview(mediaItemId)
                MediaType.TV_SHOW -> tvDetailsRepository.deleteTvReview(mediaItemId)
            }

            when (result) {
                is ReviewResult.Success -> {
                    val latestState = uiState.value
                    if (latestState is DetailsContract.State.Success) {
                        setState {
                            latestState.copy(
                                reviewSubmitting = false,
                                userReview = null,
                                showDeleteReviewConfirmation = false // Ensure dialog stays dismissed
                            )
                        }
                    }
                    setEffect { DetailsContract.Effects.ReviewSubmittedSuccessfully }
                    // Reload reviews
                    loadReviews()
                }
                is ReviewResult.Error -> {
                    val latestState = uiState.value
                    if (latestState is DetailsContract.State.Success) {
                        setState {
                            latestState.copy(
                                reviewSubmitting = false,
                                showDeleteReviewConfirmation = false // Ensure dialog is dismissed even on error
                            )
                        }
                    }
                    setEffect { DetailsContract.Effects.ShowReviewError(result.error) }
                }
            }
        }
    }

    private fun onDismissDeleteConfirmation() {
        val currentState = uiState.value
        if (currentState is DetailsContract.State.Success) {
            setState {
                currentState.copy(showDeleteReviewConfirmation = false)
            }
        }
    }

    private fun onWatchTrailer(trailerLink: String) {
        viewModelScope.launch {
            setEffect { DetailsContract.Effects.OpenTrailer(trailerLink) }
        }
    }

}