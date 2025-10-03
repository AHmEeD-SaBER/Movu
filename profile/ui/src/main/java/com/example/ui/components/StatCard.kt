package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.Black
import com.example.core_ui.R as CoreUiR

@Composable
fun StatCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_12)).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = title,
                style = AppTypography.bt2,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = count.toString(),
                style = AppTypography.h1,
                color = Black.copy(alpha = 0.6f)
            )

        }
    }
}