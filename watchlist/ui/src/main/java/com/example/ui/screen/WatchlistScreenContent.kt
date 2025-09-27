package com.example.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.core_ui.R as CoreR
import com.example.ui.R
import com.example.core_ui.theme.AppTypography
import com.example.ui.WatchlistContract
import com.example.ui.components.WatchlistMoviesSection
import com.example.ui.components.WatchlistTvSection
import com.example.core_ui.components.NormalAppBar
import com.example.ui.components.EmptyStateMessage
import com.example.ui.components.SelectionModeAppBar

@Composable
fun WatchlistScreenContent(
    modifier: Modifier = Modifier,
    state: WatchlistContract.State.Success,
    onEvent: (WatchlistContract.Events) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemsToDeleteCount by remember { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        if (state.isSelectionMode) {
            SelectionModeAppBar(
                selectedCount = state.selectedMovieIds.size + state.selectedTvShowIds.size,
                onCloseSelection = { onEvent(WatchlistContract.Events.ExitSelectionMode) },
                onSelectAll = { onEvent(WatchlistContract.Events.SelectAllItems) },
                onDeselectAll = { onEvent(WatchlistContract.Events.DeselectAllItems) },
                onDelete = {
                    itemsToDeleteCount = state.selectedMovieIds.size + state.selectedTvShowIds.size
                    showDeleteDialog = true
                }
            )
        } else {
            NormalAppBar()
        }


        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(CoreR.dimen.padding_12))
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(CoreR.dimen.padding_16))
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            text = "Movies (${state.movies.size})",
                            style = AppTypography.bt2,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            text = "TV Shows (${state.tvShows.size})",
                            style = AppTypography.bt2,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                when (selectedTabIndex) {
                    0 -> {
                        if (state.movies.isEmpty()) {
                            EmptyStateMessage(
                                message = stringResource(R.string.no_movies_in_watchlist),
                                subtitle = stringResource(R.string.add_favorite_movies_prompt)
                            )
                        } else {
                            WatchlistMoviesSection(
                                items = state.movies,
                                isSelectionMode = state.isSelectionMode,
                                selectedIds = state.selectedMovieIds,
                                onEvent = onEvent
                            )
                        }
                    }
                    1 -> {
                        if (state.tvShows.isEmpty()) {
                            EmptyStateMessage(
                                message = stringResource(R.string.no_tv_shows_in_watchlist),
                                subtitle = stringResource(R.string.add_favorite_tv_shows_prompt)
                            )
                        } else {
                            WatchlistTvSection(
                                items = state.tvShows,
                                isSelectionMode = state.isSelectionMode,
                                selectedIds = state.selectedTvShowIds,
                                onEvent = onEvent
                            )
                        }
                    }
                }

                if (state.isSelectionMode && (state.selectedMovieIds.isNotEmpty() || state.selectedTvShowIds.isNotEmpty())) {
                    FloatingActionButton(
                        onClick = {
                            itemsToDeleteCount = state.selectedMovieIds.size + state.selectedTvShowIds.size
                            showDeleteDialog = true
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(dimensionResource(CoreR.dimen.padding_16)),
                        containerColor = MaterialTheme.colorScheme.error
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_selected),
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.delete_dialog_title)) },
            text = {
                Text(
                    if (itemsToDeleteCount == 1) {
                        stringResource(R.string.delete_dialog_message_single)
                    } else {
                        stringResource(R.string.delete_dialog_message_multiple, itemsToDeleteCount)
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onEvent(WatchlistContract.Events.DeleteSelectedItems)
                    }
                ) {
                    Text(stringResource(R.string.action_delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        )
    }
}




