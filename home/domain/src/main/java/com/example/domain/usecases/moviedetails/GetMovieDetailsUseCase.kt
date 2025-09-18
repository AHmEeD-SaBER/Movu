package com.example.domain.usecases.moviedetails

import com.example.domain.models.MediaResult
import com.example.domain.models.Movie
import com.example.domain.repositories.IMovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(private val repository: IMovieDetailsRepository) :
    IGetMovieDetailsUseCase {
    override suspend operator fun invoke(movieId: Int): Flow<MediaResult<Movie>> {
        return repository.getMovieDetails(movieId)
    }
}
