package com.example.ui.screen

import androidx.lifecycle.viewModelScope
import com.example.core_domain.MediaType
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.SearchError
import com.example.domain.usecases.search.ISearchMoviesUseCase
import com.example.domain.usecases.search.ISearchTvShowsUseCase
import com.example.domain.models.SearchResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchMoviesUseCase: ISearchMoviesUseCase,
    private val searchTvShowsUseCase: ISearchTvShowsUseCase
) : BaseViewModel<SearchContract.Events, SearchContract.State, SearchContract.Effects>() {

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotBlank() && query.length >= 2) {
                        performSearch(query)
                    } else if (query.isBlank()) {
                        setState { SearchContract.State.Idle }
                    }
                }
        }
    }

    override fun setInitialState(): SearchContract.State = SearchContract.State.Idle

    override fun handleEvent(event: SearchContract.Events) {
        when (event) {
            is SearchContract.Events.SearchQueryChanged -> {
                onQueryChanged(event.query)
            }
            is SearchContract.Events.MediaItemClicked -> {
                onItemClicked(event.mediaItemId, event.mediaType)
            }
            SearchContract.Events.ClearSearch -> {
                onCLearSearch()
            }
            SearchContract.Events.Retry -> {
                onRetry()
            }
        }
    }

    private fun onRetry() {
        val currentQuery = searchQuery.value
        if (currentQuery.isNotBlank() && currentQuery.length >= 2) {
            performSearch(currentQuery)
        } else {
            setState { SearchContract.State.Idle }
        }
    }

    private fun onCLearSearch() {
        searchQuery.value = ""
        setState { SearchContract.State.Idle }
    }

    private fun onQueryChanged(query: String) {
        searchQuery.value = query
    }
    private fun onItemClicked(id: Int, mediaType: MediaType) {
        viewModelScope.launch {
            setEffect { SearchContract.Effects.NavigateToDetails(id, mediaType) }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            setState { SearchContract.State.Loading }

            try {
                val moviesFlow = searchMoviesUseCase(query)
                val tvShowsFlow = searchTvShowsUseCase(query)

                var movieResults = emptyList<SearchContract.SearchResultItem>()
                var tvResults = emptyList<SearchContract.SearchResultItem>()
                var hasError = false
                var searchError: SearchError? = null

                moviesFlow.collect { moviesResult ->
                    when (moviesResult) {
                        is SearchResult.Success -> {
                            movieResults = moviesResult.data.items.map { searchItem ->
                                SearchContract.SearchResultItem(
                                    searchItem = searchItem,
                                    mediaType = MediaType.MOVIE
                                )
                            }
                        }
                        is SearchResult.Error -> {
                            hasError = true
                            searchError = moviesResult.error
                        }
                    }
                }

                tvShowsFlow.collect { tvResult ->
                    when (tvResult) {
                        is SearchResult.Success -> {
                            tvResults = tvResult.data.items.map { searchItem ->
                                SearchContract.SearchResultItem(
                                    searchItem = searchItem,
                                    mediaType = MediaType.TV_SHOW
                                )
                            }
                        }
                        is SearchResult.Error -> {
                            if (!hasError) {
                                hasError = true
                                searchError = tvResult.error
                            }
                        }
                    }
                }

                if (hasError && movieResults.isEmpty() && tvResults.isEmpty()) {
                    setState { SearchContract.State.Error(searchError!!) }
                } else {
                    val combinedResults = (movieResults + tvResults)
                        .sortedByDescending { it.searchItem.popularity }

                    if (combinedResults.isEmpty()) {
                        setState { SearchContract.State.EmptyResults }
                    } else {
                        setState { SearchContract.State.Success(combinedResults) }
                    }
                }

            } catch (exception: Exception) {

            }
        }
    }
}
