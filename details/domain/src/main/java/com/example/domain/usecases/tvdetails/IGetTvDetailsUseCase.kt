package com.example.domain.usecases.tvdetails

import com.example.domain.DetailsResult
import com.example.domain.Tv
import kotlinx.coroutines.flow.Flow

interface IGetTvDetailsUseCase {
    suspend operator fun invoke(tvId: Int): Flow<DetailsResult<Tv>>
}
