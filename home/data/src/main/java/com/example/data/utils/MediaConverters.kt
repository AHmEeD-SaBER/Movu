package com.example.data.utils

import com.example.domain.models.*
import com.example.core_data.models.movies.Movie as DataMovie
import com.example.core_data.models.movies.MoviesResponse as DataMoviesResponse
import com.example.core_data.models.tv.TvShow as DataTvShow
import com.example.core_data.models.tv.TvResponse as DataTvResponse
import com.example.core_data.R

// Extension functions to convert data models to domain models

// Convert Movie list response to MediaItemsResponse
fun DataMoviesResponse.toDomainModel(): MediaItemsResponse {
    return MediaItemsResponse(
        items = movies?.filterNotNull()?.map { it.toDomainModel() } ?: emptyList()
    )
}

// Convert single Movie to MediaItem
fun DataMovie.toDomainModel(): MediaItem {
    return MediaItem(
        id = id ?: 0,
        title = title ?: "",
        rating = voteAverage ?: 0.0,
        image = posterPath ?: "",
        horizontalImage = backdropPath
    )
}

// Convert TV shows list response to MediaItemsResponse
fun DataTvResponse.toDomainModel(): MediaItemsResponse {
    return MediaItemsResponse(
        items = tvShows?.filterNotNull()?.map { it.toDomainModel() } ?: emptyList()
    )
}

// Convert single TvShow to MediaItem
fun DataTvShow.toDomainModel(): MediaItem {
    return MediaItem(
        id = id ?: 0,
        title = name ?: "",
        rating = voteAverage ?: 0.0,
        image = posterPath ?: ""
    )
}



// Extension function to convert Result to MediaResult using the new MediaError class
fun <T> Result<T>.toMediaResult(): MediaResult<T> {
    return if (isSuccess) {
        MediaResult.Success(getOrThrow())
    } else {
        MediaResult.Error(MediaError(
            titleRes = R.string.error_title_unknown,
            subtitleRes = R.string.error_subtitle_unknown
        ))
    }
}
