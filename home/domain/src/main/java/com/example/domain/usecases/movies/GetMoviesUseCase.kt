package com.example.domain.usecases.movies

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import com.example.domain.repositories.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(private val repository: IMoviesRepository) : IGetMoviesUseCase {
    override suspend operator fun invoke(): Flow<MediaResult<MediaItemsResponse>> {
        return repository.getMovies()
    }
}

