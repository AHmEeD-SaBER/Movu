package com.example.domain.models

import com.example.core_domain.CustomError

// SearchError class to hold title and subtitle string resource IDs
data class SearchError(
    override val titleRes: Int,
    override val subtitleRes: Int
) : CustomError(titleRes, subtitleRes)

// Custom result class for search operations
sealed class SearchResult<out T> {
    data class Success<T>(val data: T) : SearchResult<T>()
    data class Error(val error: SearchError) : SearchResult<Nothing>()
}

// Base domain DTO for search result items
data class SearchItem(
    val id: Int,
    val title: String,
    val rating: Double,
    val image: String,
    val horizontalImage: String? = null,
    val popularity: Double = 0.0,
)

// Response DTOs for search API responses
data class SearchItemsResponse(
    val items: List<SearchItem>
)
