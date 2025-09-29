package com.example.domain.repositories

import com.example.domain.models.SearchResult
import com.example.domain.models.SearchItemsResponse
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun searchMovies(query: String): Flow<SearchResult<SearchItemsResponse>>
    fun searchTvShows(query: String): Flow<SearchResult<SearchItemsResponse>>
}
