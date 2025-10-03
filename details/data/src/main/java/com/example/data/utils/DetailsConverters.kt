package com.example.data.utils

import com.example.core_data.models.moviedetails.MovieDetailsResponse
import com.example.core_data.models.tvdetails.TvShowDetails
import com.example.core_data.models.credits.CreditsResponse
import com.example.core_data.models.videos.VideosResponse
import com.example.domain.Movie
import com.example.domain.Tv
import com.example.domain.Credits
import com.example.domain.CastMember
import com.example.domain.CrewMember
import com.example.domain.ProductionCompany
import com.example.domain.Review
import com.example.user_preferences.models.FirebaseReview


fun CreditsResponse.toCredits(): Credits {
    return Credits(
        cast = cast?.filterNotNull()?.map { castItem ->
            CastMember(
                id = castItem.id ?: 0,
                name = castItem.name ?: "",
                character = castItem.character ?: "",
                profilePath = castItem.profilePath
            )
        } ?: emptyList(),
        crew = crew?.filterNotNull()?.map { crewItem ->
            CrewMember(
                id = crewItem.id ?: 0,
                name = crewItem.name ?: "",
                job = crewItem.job ?: "",
                department = crewItem.department ?: "",
                profilePath = crewItem.profilePath
            )
        } ?: emptyList()
    )
}

fun VideosResponse.getTrailerLink(): String? {
    val trailer = results?.firstOrNull { video ->
        video?.type == "Trailer" && video.site == "YouTube"
    }
    return trailer?.key?.let { "https://www.youtube.com/watch?v=$it" }
}

fun MovieDetailsResponse.toDomainModel(credits: Credits, trailer: String? = null): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        image = posterPath ?: "",
        genres = genres?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        productionCompanies = productionCompanies?.map {
            ProductionCompany(
                id = it?.id ?: 0,
                name = it?.name ?: "",
                logoPath = it?.logoPath
            )
        } ?: emptyList(),
        length = runtime ?: 0,
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.englishName } ?: emptyList(),
        plot = overview ?: "",
        credits = credits,
        voteCount = voteCount ?: 0,
        trailerLink = trailer
    )
}

fun TvShowDetails.toDomainModel(credits: Credits, trailer: String?): Tv {
    val episodeRunTime = when {
        episodeRunTime?.isNotEmpty() == true -> {
            // Try to get the first runtime as Int, fallback to 0
            when (val firstRuntime = (episodeRunTime as List<Any?>).firstOrNull()) {
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
        productionCompanies = productionCompanies?.map {
            ProductionCompany(
                id = it?.id ?: 0,
                name = it?.name ?: "",
                logoPath = it?.logoPath
            )
        } ?: emptyList(),
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.englishName } ?: emptyList(),
        plot = overview ?: "",
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0,
        credits = credits,
        trailerLink = trailer
    )
}

fun FirebaseReview.toDomainModel(): Review {
    return Review(
        userId = userId,
        userName = userName,
        mediaId = mediaId,
        rating = rating,
        reviewText = reviewText,
        timestamp = timestamp
    )
}
