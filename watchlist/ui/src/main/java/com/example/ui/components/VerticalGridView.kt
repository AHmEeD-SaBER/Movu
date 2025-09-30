package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.core_ui.R
import com.example.domain.models.WatchlistItem

@Composable
fun VerticalGridView(
    modifier: Modifier = Modifier,
    items: List<WatchlistItem>,
    isSelectionMode: Boolean = false,
    selectedIds: Set<Int> = emptySet(),
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_64)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_12)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_12)),
        content = {
            items(items) { item ->
                WatchlistItemCard(
                    mediaItem = item,
                    isSelectionMode = isSelectionMode,
                    isSelected = selectedIds.contains(item.id),
                    onItemClicked = onItemClick,
                    onItemLongClicked = onItemLongClick
                )
            }
        }
    )

}