package com.example.domain.usecases.tvdetails

import com.example.domain.models.MediaResult
import com.example.domain.models.Tv
import com.example.domain.repositories.ITvDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetTvDetailsUseCase(private val repository: ITvDetailsRepository) : IGetTvDetailsUseCase {
    override suspend operator fun invoke(tvId: Int): Flow<MediaResult<Tv>> {
        return repository.getTvDetails(tvId)
    }
}
