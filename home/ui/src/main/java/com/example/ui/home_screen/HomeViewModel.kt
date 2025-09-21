package com.example.ui.home_screen

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.MediaError
import com.example.domain.usecases.movies.IGetMoviesUseCase
import com.example.domain.usecases.tv.IGetTvShowsUseCase
import com.example.domain.models.MediaType
import com.example.domain.models.MediaItem
import com.example.domain.models.MediaResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moviesUseCase: IGetMoviesUseCase,
    private val tvUseCase: IGetTvShowsUseCase
) : BaseViewModel<HomeContract.Events, HomeContract.State, HomeContract.Effects>() {

    init {
        loadData()
    }

    private fun onRetry() {
        loadData()
    }

    private fun onItemClicked(id: Int, mediaType: MediaType) {
        viewModelScope.launch {
            setEffect { HomeContract.Effects.NavigateToDetail(id, mediaType) }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            setState { HomeContract.State.Loading }

            try {
                val moviesFlow = moviesUseCase()
                val tvShowsFlow = tvUseCase()

                var moviesData: List<MediaItem> = emptyList()
                var tvShowsData: List<MediaItem> = emptyList()
                var moviesError: MediaError? = null
                var tvError: MediaError? = null

                moviesFlow.collect { moviesResult ->
                    when (moviesResult) {
                        is MediaResult.Success -> {
                            moviesData = moviesResult.data.items
                        }

                        is MediaResult.Error -> {
                            moviesError = moviesResult.error
                        }
                    }
                }

                tvShowsFlow.collect { tvResult ->
                    when (tvResult) {
                        is MediaResult.Success -> {
                            tvShowsData = tvResult.data.items
                        }

                        is MediaResult.Error -> {
                            tvError = tvResult.error
                        }
                    }
                }



                setState {
                    when {
                        moviesError != null && tvError != null -> HomeContract.State.Error(moviesError)
                        moviesError != null && tvError == null -> HomeContract.State.Success(moviesData, tvShowsData, moviesData.random())
                        moviesError == null && tvError != null -> HomeContract.State.Success(moviesData, tvShowsData, moviesData.random())
                        else -> HomeContract.State.Success(moviesData, tvShowsData, moviesData.random())
                    }
                }

            } catch (e: Exception) {
                setState {
                    HomeContract.State.Error(
                        MediaError(
                            titleRes = android.R.string.dialog_alert_title,
                            subtitleRes = android.R.string.unknownName
                        )
                    )
                }
            }
        }
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State.Idle
    }

    override fun handleEvent(event: HomeContract.Events) {
        when (event) {
            HomeContract.Events.LoadData -> loadData()
            is HomeContract.Events.MediaItemClicked -> onItemClicked(
                event.mediaItemId,
                event.mediaType
            )

            HomeContract.Events.Retry -> onRetry()
        }
    }
}