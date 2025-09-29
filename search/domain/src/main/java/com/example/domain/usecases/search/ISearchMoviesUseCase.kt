package com.example.domain.usecases.search

import com.example.domain.models.SearchResult
import com.example.domain.models.SearchItemsResponse
import kotlinx.coroutines.flow.Flow

interface ISearchMoviesUseCase {
    suspend operator fun invoke(query: String): Flow<SearchResult<SearchItemsResponse>>
}
