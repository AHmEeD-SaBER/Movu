package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme
import com.example.domain.models.ReviewStatistics
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun ReviewStatsSection(
    reviewStatistics: ReviewStatistics?,
    modifier: Modifier = Modifier
) {
    reviewStatistics?.let { stats ->
        if (stats.totalReviews > 0) {
            ReviewStatisticsCard(
                statistics = stats,
                modifier = modifier
            )
        } else {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_16)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(CoreUiR.dimen.padding_2))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(CoreUiR.dimen.padding_32)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
                    ) {
                        Text(
                            text = stringResource(R.string.no_reviews_yet_profile),
                            style = AppTypography.bt2,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = stringResource(R.string.start_reviewing),
                            style = AppTypography.bt4,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewStatsSectionPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewStatsSection(
                reviewStatistics = ReviewStatistics(
                    totalReviews = 25,
                    movieReviews = 15,
                    tvReviews = 10,
                    averageRating = 4.2f
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewStatsSectionEmptyPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewStatsSection(
                reviewStatistics = ReviewStatistics(
                    totalReviews = 0,
                    movieReviews = 0,
                    tvReviews = 0,
                    averageRating = 0f
                )
            )
        }
    }
}
