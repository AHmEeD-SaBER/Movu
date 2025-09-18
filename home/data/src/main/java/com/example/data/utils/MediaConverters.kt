package com.example.data.utils

import com.example.domain.models.*
import models.movies.Movie as DataMovie
import models.movies.MoviesResponse as DataMoviesResponse
import models.tv.TvShow as DataTvShow
import models.tv.TvResponse as DataTvResponse
import models.moviedetails.MovieDetailsResponse as DataMovieDetailsResponse
import models.tvdetails.TvShowDetails as DataTvShowDetails
import com.example.data.R

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
        image = posterPath ?: ""
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

// Convert MovieDetailsResponse to Movie domain model
fun DataMovieDetailsResponse.toDomainModel(): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        image = posterPath ?: "",
        genres = genres?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        productionCompanies = productionCompanies?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        length = runtime ?: 0,
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        plot = overview ?: ""
    )
}

// Convert TvShowDetails to Tv domain model
fun DataTvShowDetails.toDomainModel(): Tv {
    val episodeRunTime = when {
        episodeRunTime?.isNotEmpty() == true -> {
            // Try to get the first runtime as Int, fallback to 0
            when (val firstRuntime = episodeRunTime.firstOrNull()) {
                is Number -> firstRuntime.toInt()
                is String -> firstRuntime.toIntOrNull() ?: 0
                else -> 0
            }
        }
        else -> 0
    }

    return Tv(
        id = id ?: 0,
        title = name ?: "",
        image = posterPath ?: "",
        genres = genres?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        productionCompanies = productionCompanies?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        plot = overview ?: "",
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0
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
