package com.example.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.base.Routes
import com.example.domain.DetailsResult
import kotlinx.coroutines.launch
import androidx.navigation.toRoute

class DetailsViewModel(
    private val getMediaDetailsUseCase: com.example.domain.usecases.mediadetails.IGetMediaDetailsUseCase,
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

    private fun loadData(itemId: Int = mediaItemId, type: com.example.core_domain.MediaType = mediaType) {
        viewModelScope.launch {
            setState { DetailsContract.State.Loading }

            try {
                val detailsFlow = getMediaDetailsUseCase(itemId, type)

                detailsFlow.collect { result ->
                    when (result) {
                        is DetailsResult.Success -> {
                            setState { DetailsContract.State.Success(result.data) }
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
}