package com.example.data.data_sources

import com.example.core_data.models.movies.MoviesResponse
import com.example.core_data.models.tv.TvResponse
import com.example.core_data.network.IRetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRemoteDataSource(
    private val retrofitService: IRetrofitService
) : ISearchRemoteDataSource {

    override fun searchMovies(query: String): Flow<MoviesResponse> = flow {
        emit(retrofitService.searchMovies(query))
    }

    override fun searchTvShows(query: String): Flow<TvResponse> = flow {
        emit(retrofitService.searchTvShows(query))
    }
}
