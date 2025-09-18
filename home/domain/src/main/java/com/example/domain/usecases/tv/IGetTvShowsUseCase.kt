package com.example.domain.usecases.tv

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import kotlinx.coroutines.flow.Flow

interface IGetTvShowsUseCase {
    suspend operator fun invoke(): Flow<MediaResult<MediaItemsResponse>>
}
