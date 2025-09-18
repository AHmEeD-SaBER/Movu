package com.example.domain.usecases.movies

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import kotlinx.coroutines.flow.Flow

interface IGetMoviesUseCase {
    suspend operator fun invoke(): Flow<MediaResult<MediaItemsResponse>>
}
