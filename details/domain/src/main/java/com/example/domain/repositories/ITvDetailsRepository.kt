package com.example.domain.repositories

import com.example.domain.DetailsResult
import com.example.domain.Tv
import kotlinx.coroutines.flow.Flow

interface ITvDetailsRepository {
    fun getTvDetails(tvId: Int): Flow<DetailsResult<Tv>>
}
