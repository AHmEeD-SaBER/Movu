package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R as CoreR

@Composable
fun EmptyStateMessage(
    message: String,
    subtitle: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = message,
                style = AppTypography.sh2,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                style = AppTypography.bt3,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = dimensionResource(CoreR.dimen.padding_8))
            )
        }
    }
}