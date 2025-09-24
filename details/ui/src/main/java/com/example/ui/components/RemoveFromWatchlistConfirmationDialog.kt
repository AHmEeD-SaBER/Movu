package com.example.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.core_ui.theme.AppTypography
import com.example.ui.R

@Composable
fun RemoveFromWatchlistConfirmationDialog(
    itemTitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.tertiary,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
        title = {
            Text(
                text = stringResource(R.string.remove_from_watchlist_title),
                style = AppTypography.bt2,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = stringResource(R.string.remove_from_watchlist_message, itemTitle),
                style = AppTypography.bt4,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(R.string.remove),
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}
