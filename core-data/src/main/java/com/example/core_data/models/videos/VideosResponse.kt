package com.example.core_data.models.videos


import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Result?>?
)