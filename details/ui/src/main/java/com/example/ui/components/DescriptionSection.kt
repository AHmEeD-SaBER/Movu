package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.core_ui.R as CoreUiR
import com.example.core_ui.theme.AppTypography
import com.example.ui.R

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    plot: String,
    genres: List<String>,
    maxLines: Int = Int.MAX_VALUE,
) {
    Column {
        Text(
            text = stringResource(R.string.label_description),
            style = AppTypography.h7,
            modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_8))
        )

        Text(
            text = plot,
            style = AppTypography.bt3.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
        )

        if (genres.isNotEmpty()) {
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_12)))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
            ) {
                items(genres) { genre ->
                    Surface(
                        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_16)),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = genre,
                            style = AppTypography.bt7,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(CoreUiR.dimen.padding_12),
                                vertical = dimensionResource(CoreUiR.dimen.padding_6)
                            )
                        )
                    }
                }
            }
        }
    }
}
