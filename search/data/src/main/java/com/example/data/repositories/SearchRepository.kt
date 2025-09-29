package com.example.data.repositories

import com.example.core_data.R
import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.SearchError
import com.example.domain.models.SearchItemsResponse
import com.example.domain.models.SearchResult
import com.example.domain.repositories.ISearchRepository
import com.example.search.data.utils.toSearchItemsResponse
import com.example.data.data_sources.ISearchRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepository(
    private val searchRemoteDataSource: ISearchRemoteDataSource,
    private val networkMonitor: INetworkMonitor
) : ISearchRepository {

    override fun searchMovies(query: String): Flow<SearchResult<SearchItemsResponse>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                SearchResult.Error(
                    SearchError(
                        R.string.error_title_network,
                        R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }
        searchRemoteDataSource.searchMovies(query).collect { moviesResponse ->
            val result = try {
                SearchResult.Success(moviesResponse.toSearchItemsResponse())
            } catch (e: Exception) {
                SearchResult.Error(
                    SearchError(
                        R.string.error_title_unknown,
                        R.string.error_subtitle_unknown
                    )
                )
            }
            emit(result)
        }
    }

    override fun searchTvShows(query: String): Flow<SearchResult<SearchItemsResponse>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(
                SearchResult.Error(
                    SearchError(
                        R.string.error_title_network,
                        R.string.error_subtitle_network
                    )
                )
            )
            return@flow
        }
        searchRemoteDataSource.searchTvShows(query).collect { tvResponse ->
            val result = try {
                SearchResult.Success(tvResponse.toSearchItemsResponse())
            } catch (e: Exception) {
                SearchResult.Error(
                    SearchError(
                        R.string.error_title_unknown,
                        R.string.error_subtitle_unknown
                    )
                )
            }
            emit(result)
        }
    }
}