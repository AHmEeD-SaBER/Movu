package com.example.domain.usecases.tvdetails


import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.repositories.ITvDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetTvDetailsUseCase(private val repository: ITvDetailsRepository) : IGetTvDetailsUseCase {
    override suspend operator fun invoke(tvId: Int): Flow<DetailsResult<Tv>> {
        return repository.getTvDetails(tvId)
    }
}
