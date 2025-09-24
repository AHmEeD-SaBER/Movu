package com.example.data.data_sources.videosdatasource

import com.example.core_data.models.videos.VideosResponse
import kotlinx.coroutines.flow.Flow

interface IVideosDataSource {
    fun getMovieVideos(mediaId: Int) : Flow<VideosResponse>
    fun getTvVideos(mediaId: Int) : Flow<VideosResponse>
}