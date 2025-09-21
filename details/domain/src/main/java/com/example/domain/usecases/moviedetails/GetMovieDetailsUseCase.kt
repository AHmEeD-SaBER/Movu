package com.example.domain.usecases.moviedetails

import com.example.domain.DetailsResult
import com.example.domain.Movie
import com.example.domain.repositories.IMovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(private val repository: IMovieDetailsRepository) :
    IGetMovieDetailsUseCase {
    override suspend operator fun invoke(movieId: Int): Flow<DetailsResult<Movie>> {
        return repository.getMovieDetails(movieId)
    }
}
