package com.example.domain.usecases.moviedetails

import com.example.domain.models.MediaResult
import com.example.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Flow<MediaResult<Movie>>
}
