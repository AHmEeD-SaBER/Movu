package com.example.domain.usecases.tv

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import com.example.domain.repositories.ITvRepository
import kotlinx.coroutines.flow.Flow

class GetTvShowsUseCase(private val repository: ITvRepository) : IGetTvShowsUseCase {
    override suspend operator fun invoke(): Flow<MediaResult<MediaItemsResponse>> {
        return repository.getTvShows()
    }
}
