package com.example.domain.repositories

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getMovies() : Flow<MediaResult<MediaItemsResponse>>
}