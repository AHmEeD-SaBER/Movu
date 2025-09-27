package com.example.data.utils

import com.example.user_preferences.models.FireBaseMediaItem
import com.example.user_preferences.models.WatchlistResult as FirebaseWatchlistResult
import com.example.domain.models.WatchlistItem
import com.example.domain.models.WatchlistItemsResponse
import com.example.domain.models.WatchlistResult as DomainWatchlistResult
import com.example.domain.models.WatchlistError


fun FireBaseMediaItem.toDomainModel(): WatchlistItem {
    return WatchlistItem(
        id = this.id,
        title = this.title,
        rating = this.rating,
        image = this.image,
        horizontalImage = this.horizontalImage
    )
}


fun List<FireBaseMediaItem>.toDomainResponse(): WatchlistItemsResponse {
    return WatchlistItemsResponse(
        items = this.map { it.toDomainModel() }
    )
}


fun <T> FirebaseWatchlistResult<T>.toDomainResult(
    transform: (T) -> WatchlistItemsResponse
): DomainWatchlistResult<WatchlistItemsResponse> {
    return when (this) {
        is FirebaseWatchlistResult.Success -> {
            DomainWatchlistResult.Success(transform(this.data))
        }
        is FirebaseWatchlistResult.Error -> {
            DomainWatchlistResult.Error(
                WatchlistError(
                    titleRes = this.error.titleRes,
                    subtitleRes = this.error.subtitleRes
                )
            )
        }
    }
}

fun FirebaseWatchlistResult<List<FireBaseMediaItem>>.toDomainWatchlistResult(): DomainWatchlistResult<WatchlistItemsResponse> {
    return this.toDomainResult { firebaseItems ->
        firebaseItems.toDomainResponse()
    }
}
