package com.example.data.data_sources.moviedetailsdatasource


import com.example.core_data.models.moviedetails.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.network.IRetrofitService

class MovieDetailsDataSource(private val api: IRetrofitService) : IMovieDetailsDataSource {
    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return flow {
            emit(api.getMovieDetails(movieId))
        }
    }
}