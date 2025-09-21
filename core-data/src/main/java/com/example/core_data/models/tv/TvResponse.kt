package com.example.core_data.models.tv


import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val tvShows: List<TvShow?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)