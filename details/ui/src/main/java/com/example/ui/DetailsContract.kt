package com.example.ui

import com.example.core_domain.MediaType
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.domain.DetailsError
import com.example.domain.MediaDetails
import com.example.domain.Review

class DetailsContract {
    sealed class Events : UiEvent {
        data object BackButtonClicked : Events()
        data object Retry : Events()
        data class LoadData(val mediaItemId: Int, val mediaType: MediaType) : Events()
        data class WatchTrailer(val trailerLink: String) : Events()
        data object ToggleWatchlist : Events()
        data object ConfirmRemoveFromWatchlist : Events()
        data object DismissRemoveConfirmation : Events()
        data object LoadReviews : Events()
        data object ShowAddReviewDialog : Events()
        data object DismissReviewDialog : Events()
        data class SubmitReview(val rating: Float, val reviewText: String) : Events()
        data class UpdateReview(val rating: Float, val reviewText: String) : Events()
        data object DeleteReview : Events()
        data object ConfirmDeleteReview : Events()
        data object DismissDeleteConfirmation : Events()
    }

    sealed class State : UiState {
        data object Loading : State()
        data class Error(val error: DetailsError) : State()
        data class Success(
            val details: MediaDetails,
            val isInWatchlist: Boolean = false,
            val watchlistLoading: Boolean = false,
            val showRemoveConfirmation: Boolean = false,
            val reviews: List<Review> = emptyList(),
            val reviewsLoading: Boolean = false,
            val userReview: Review? = null,
            val showReviewDialog: Boolean = false,
            val reviewSubmitting: Boolean = false,
            val showDeleteReviewConfirmation: Boolean = false
        ) : State()
        data object Idle : State()
    }

    sealed class Effects : UiEffect {
        data object NavigateBack : Effects()
        data class OpenTrailer(val trailerLink: String) : Effects()
        data class ShowWatchlistError(val error: DetailsError) : Effects()
        data class ShowReviewError(val error: DetailsError) : Effects()
        data object ReviewSubmittedSuccessfully : Effects()
    }
}