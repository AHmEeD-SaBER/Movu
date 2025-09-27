package com.example.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_domain.MediaType
import com.example.domain.models.WatchlistItem
import com.example.ui.WatchlistContract

@Composable
fun WatchlistMoviesSection(
    modifier: Modifier = Modifier,
    items: List<WatchlistItem>,
    isSelectionMode: Boolean = false,
    selectedIds: Set<Int> = emptySet(),
    onEvent: (WatchlistContract.Events) -> Unit
) {
    VerticalGridView(
        modifier = modifier,
        items = items,
        isSelectionMode = isSelectionMode,
        selectedIds = selectedIds,
        onItemClick = { mediaId ->
            onEvent(
                WatchlistContract.Events.MediaItemClicked(
                    mediaId,
                    MediaType.MOVIE
                )
            )
        },
        onItemLongClick = { mediaId ->
            onEvent(
                WatchlistContract.Events.MediaItemLongClicked(
                    mediaId,
                    MediaType.MOVIE
                )
            )
        }
    )
}