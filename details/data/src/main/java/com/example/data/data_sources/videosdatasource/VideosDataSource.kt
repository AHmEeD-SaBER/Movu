package com.example.data.data_sources.videosdatasource

import com.example.core_data.network.IRetrofitService
import kotlinx.coroutines.flow.flow

class VideosDataSource(private val api: IRetrofitService) : IVideosDataSource {
    override fun getMovieVideos(mediaId: Int) = flow {
        emit(api.getMovieVideos(mediaId))
    }

    override fun getTvVideos(mediaId: Int) = flow {
        emit(api.getTvVideos(mediaId))
    }
}