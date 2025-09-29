package com.example.domain.usecases.search

import com.example.domain.models.SearchResult
import com.example.domain.models.SearchItemsResponse
import com.example.domain.repositories.ISearchRepository
import kotlinx.coroutines.flow.Flow

class SearchTvShowsUseCase(private val repository: ISearchRepository) : ISearchTvShowsUseCase {
    override suspend operator fun invoke(query: String): Flow<SearchResult<SearchItemsResponse>> {
        return repository.searchTvShows(query)
    }
}
