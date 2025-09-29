package com.example.data.data_sources

import com.example.core_data.models.movies.MoviesResponse
import com.example.core_data.models.tv.TvResponse
import kotlinx.coroutines.flow.Flow

interface ISearchRemoteDataSource {
    fun searchMovies(query: String): Flow<MoviesResponse>
    fun searchTvShows(query: String): Flow<TvResponse>
}
