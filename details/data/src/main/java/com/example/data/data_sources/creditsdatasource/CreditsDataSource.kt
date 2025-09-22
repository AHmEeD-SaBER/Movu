package com.example.data.data_sources.creditsdatasource

import com.example.core_data.network.IRetrofitService
import kotlinx.coroutines.flow.flow

class CreditsDataSource(private val api: IRetrofitService) : ICreditsDataSource {
    override fun getMovieCredits(mediaId: Int) = flow {
        emit(api.getMovieCredits(mediaId))
    }

    override fun getTvCredits(mediaId: Int) = flow {
        emit(api.getTvCredits(mediaId))
    }
}