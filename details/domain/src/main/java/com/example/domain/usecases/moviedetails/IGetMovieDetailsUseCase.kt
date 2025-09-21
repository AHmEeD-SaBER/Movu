package com.example.domain.usecases.moviedetails

import com.example.domain.DetailsResult
import com.example.domain.Movie
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Flow<DetailsResult<Movie>>
}
