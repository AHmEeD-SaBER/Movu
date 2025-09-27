package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.outlined.Deselect
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R as CoreR
import com.example.core_ui.components.CustomAppBar
import com.example.ui.R


@Composable
fun SelectionModeAppBar(
    selectedCount: Int,
    onCloseSelection: () -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onDelete: () -> Unit
) {
    CustomAppBar(
        modifier = Modifier,
        showNavigation = false,
        title = {
            Row(
                modifier = Modifier.padding(start = dimensionResource(CoreR.dimen.padding_12)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCloseSelection) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.exit_selection_mode),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = stringResource(R.string.selected_count, selectedCount),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = dimensionResource(CoreR.dimen.padding_8))
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = dimensionResource(CoreR.dimen.padding_12)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreR.dimen.padding_8))
            ) {
                IconButton(onClick = if (selectedCount > 0) onDeselectAll else onSelectAll) {
                    Icon(
                        imageVector = if (selectedCount > 0) Icons.Outlined.Deselect else Icons.Default.SelectAll,
                        contentDescription = if (selectedCount > 0) stringResource(R.string.deselect_all) else stringResource(R.string.select_all),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                if (selectedCount > 0) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_selected),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    )
}
