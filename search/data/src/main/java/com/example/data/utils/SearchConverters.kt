package com.example.search.data.utils

import com.example.domain.models.*
import com.example.core_data.models.movies.Movie as DataMovie
import com.example.core_data.models.movies.MoviesResponse as DataMoviesResponse
import com.example.core_data.models.tv.TvShow as DataTvShow
import com.example.core_data.models.tv.TvResponse as DataTvResponse
import com.example.core_data.R

// Extension functions to convert data models to search domain models

// Convert Movie list response to SearchItemsResponse
fun DataMoviesResponse.toSearchItemsResponse(): SearchItemsResponse {
    return SearchItemsResponse(
        items = movies?.filterNotNull()?.map { it.toSearchItem() } ?: emptyList()
    )
}

// Convert single Movie to SearchItem
fun DataMovie.toSearchItem(): SearchItem {
    return SearchItem(
        id = id ?: 0,
        title = title ?: "",
        rating = voteAverage ?: 0.0,
        image = posterPath ?: "",
        horizontalImage = backdropPath,
        popularity = popularity ?: 0.0
    )
}

// Convert TV shows list response to SearchItemsResponse
fun DataTvResponse.toSearchItemsResponse(): SearchItemsResponse {
    return SearchItemsResponse(
        items = tvShows?.filterNotNull()?.map { it.toSearchItem() } ?: emptyList()
    )
}

// Convert single TvShow to SearchItem
fun DataTvShow.toSearchItem(): SearchItem {
    return SearchItem(
        id = id ?: 0,
        title = name ?: "",
        rating = voteAverage ?: 0.0,
        image = posterPath ?: "",
        horizontalImage = backdropPath,
        popularity = popularity ?: 0.0
    )
}

