package com.example.domain

interface MediaDetails {
    val id: Int
    val title: String
    val image: String
    val genres: List<String>
    val productionCompanies: List<String>
    val rating: Double
    val languages: List<String>
    val plot: String
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
    override val plot: String
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
    val numberOfSeasons: Int
) : MediaDetails

sealed class DetailsResult<out T> {
    data class Success<T>(val data: T) : DetailsResult<T>()
    data class Error(val error: DetailsError) : DetailsResult<Nothing>()
}

data class DetailsError(
    val titleRes: Int,
    val subtitleRes: Int
)