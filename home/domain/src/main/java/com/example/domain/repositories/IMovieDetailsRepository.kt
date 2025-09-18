package com.example.domain.repositories

import com.example.domain.models.MediaResult
import com.example.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<MediaResult<Movie>>
}
