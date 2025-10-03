package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.Black
import com.example.core_ui.theme.MovuTheme
import com.example.domain.models.ReviewStatistics
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@SuppressLint("DefaultLocale")
@Composable
fun ReviewStatisticsCard(
    statistics: ReviewStatistics,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_16)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(CoreUiR.dimen.padding_4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_large_16))
        ) {
            Text(
                text = stringResource(R.string.review_statistics),
                style = AppTypography.h7,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_12))
            ) {
                StatItem(
                    title = stringResource(R.string.total_reviews),
                    value = statistics.totalReviews.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatItem(
                    title = stringResource(R.string.movie_reviews),
                    value = statistics.movieReviews.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatItem(
                    title = stringResource(R.string.tv_reviews),
                    value = statistics.tvReviews.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(
                thickness = dimensionResource(CoreUiR.dimen.padding_2).value.toInt().dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.average_rating),
                    style = AppTypography.bt3,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Surface(
                    shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_12)),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(CoreUiR.dimen.padding_12),
                            vertical = dimensionResource(CoreUiR.dimen.spacing_medium_8)
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_6))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(R.string.average_rating),
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_20))
                        )
                        Text(
                            text = String.format("%.1f", statistics.averageRating),
                            style = AppTypography.bt2,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(R.string.rating_out_of),
                            style = AppTypography.bt4,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_12)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CoreUiR.dimen.padding_12)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_4))
        ) {
            Text(
                text = value,
                style = AppTypography.h2,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = title,
                style = AppTypography.bt5,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewStatisticsCardPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewStatisticsCard(
                statistics = ReviewStatistics(
                    totalReviews = 42,
                    movieReviews = 28,
                    tvReviews = 14,
                    averageRating = 4.3f
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewStatisticsCardNoReviewsPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewStatisticsCard(
                statistics = ReviewStatistics(
                    totalReviews = 0,
                    movieReviews = 0,
                    tvReviews = 0,
                    averageRating = 0f
                )
            )
        }
    }
}
