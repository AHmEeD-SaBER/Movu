package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<DetailsResult<Movie>>
}
