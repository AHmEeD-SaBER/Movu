package com.example.details.data.utils

import com.example.core_data.models.moviedetails.MovieDetailsResponse
import com.example.core_data.models.tvdetails.TvShowDetails
import com.example.core_data.models.credits.CreditsResponse
import com.example.domain.Movie
import com.example.domain.Tv
import com.example.domain.Credits
import com.example.domain.CastMember
import com.example.domain.CrewMember

// Convert CreditsResponse to Credits domain model
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

// Convert MovieDetailsResponse to Movie domain model
fun MovieDetailsResponse.toDomainModel(credits: Credits): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        image = posterPath ?: "",
        genres = genres?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        productionCompanies = productionCompanies?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        length = runtime ?: 0,
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        plot = overview ?: "",
        credits = credits
    )
}

// Convert TvShowDetails to Tv domain model
fun TvShowDetails.toDomainModel(credits: Credits): Tv {
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
        productionCompanies = productionCompanies?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        rating = voteAverage ?: 0.0,
        languages = spokenLanguages?.filterNotNull()?.mapNotNull { it.name } ?: emptyList(),
        plot = overview ?: "",
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0,
        credits = credits
    )
}