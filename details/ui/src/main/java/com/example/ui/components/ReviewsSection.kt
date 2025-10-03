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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.Black
import com.example.core_ui.theme.MovuTheme
import com.example.domain.Review
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun ReviewsSection(
    reviews: List<Review>,
    currentUserId: String?,
    isLoading: Boolean = false,
    onDeleteReview: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_16)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.reviews),
                    style = AppTypography.bt2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (reviews.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.spacing_medium_8)),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${reviews.size}",
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(CoreUiR.dimen.spacing_medium_8),
                                vertical = dimensionResource(CoreUiR.dimen.padding_4)
                            ),
                            style = AppTypography.bt4,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_large_16)))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(CoreUiR.dimen.layout_height_250)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                reviews.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(CoreUiR.dimen.layout_height_150)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
                        ) {
                            Text(
                                text = stringResource(R.string.no_reviews_yet),
                                style = AppTypography.bt3,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Text(
                                text = stringResource(R.string.be_first_to_review),
                                style = AppTypography.bt5,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_12))
                    ) {
                        reviews.forEach { review ->
                            ReviewItem(
                                review = review,
                                isCurrentUser = review.userId == currentUserId,
                                onDeleteClick = onDeleteReview
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewsSectionPreview() {
    MovuTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewsSection(
                reviews = listOf(
                    Review(
                        userId = "user1",
                        userName = "John Doe",
                        mediaId = 550,
                        rating = 4.5f,
                        reviewText = "Amazing movie!",
                        timestamp = System.currentTimeMillis()
                    ),
                    Review(
                        userId = "user2",
                        userName = "Jane Smith",
                        mediaId = 550,
                        rating = 5.0f,
                        reviewText = "Best film ever!",
                        timestamp = System.currentTimeMillis()
                    )
                ),
                currentUserId = "user1"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewsSectionEmptyPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewsSection(
                reviews = emptyList(),
                currentUserId = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewsSectionLoadingPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            ReviewsSection(
                reviews = emptyList(),
                currentUserId = null,
                isLoading = true
            )
        }
    }
}
