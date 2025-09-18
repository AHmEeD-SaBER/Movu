package com.example.domain.repositories

import com.example.domain.models.MediaResult
import com.example.domain.models.Tv
import kotlinx.coroutines.flow.Flow

interface ITvDetailsRepository {
    fun getTvDetails(tvId: Int): Flow<MediaResult<Tv>>
}
