package com.example.domain

import com.example.core_domain.CustomError

data class Credits(
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?
)

data class CrewMember(
    val id: Int,
    val name: String,
    val job: String,
    val department: String,
    val profilePath: String?
)

interface MediaDetails {
    val id: Int
    val title: String
    val image: String
    val genres: List<String>
    val productionCompanies: List<String>
    val rating: Double
    val languages: List<String>
    val plot: String
    val credits: Credits
}

data class Movie(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val genres: List<String>,
    override val productionCompanies: List<String>,
    val length: Int,
    override val rating: Double,
    override val languages: List<String>,
    override val plot: String,
    override val credits: Credits
) : MediaDetails

data class Tv(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val genres: List<String>,
    override val productionCompanies: List<String>,
    override val rating: Double,
    override val languages: List<String>,
    override val plot: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    override val credits: Credits
) : MediaDetails

sealed class DetailsResult<out T> {
    data class Success<T>(val data: T) : DetailsResult<T>()
    data class Error(val error: DetailsError) : DetailsResult<Nothing>()
}

data class DetailsError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)