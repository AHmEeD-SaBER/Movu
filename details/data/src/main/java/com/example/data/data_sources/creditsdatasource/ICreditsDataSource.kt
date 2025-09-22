package com.example.data.data_sources.creditsdatasource

import com.example.core_data.models.credits.CreditsResponse
import kotlinx.coroutines.flow.Flow

interface ICreditsDataSource {
    fun getMovieCredits(mediaId: Int) : Flow<CreditsResponse>

    fun getTvCredits(mediaId: Int) : Flow<CreditsResponse>
}