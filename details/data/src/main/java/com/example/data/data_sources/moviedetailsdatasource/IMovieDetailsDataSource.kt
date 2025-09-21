package com.example.data.data_sources.moviedetailsdatasource

import com.example.core_data.models.moviedetails.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsDataSource {
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
}
