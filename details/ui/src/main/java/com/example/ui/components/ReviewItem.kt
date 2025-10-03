package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme
import com.example.domain.Review
import com.example.ui.R
import java.text.SimpleDateFormat
import java.util.*
import com.example.core_ui.R as CoreUiR

@SuppressLint("DefaultLocale")
@Composable
fun ReviewItem(
    review: Review,
    isCurrentUser: Boolean = false,
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_12)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(CoreUiR.dimen.padding_2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.userName,
                        style = AppTypography.bt3,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = formatTimestamp(review.timestamp),
                        style = AppTypography.bt5,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
                ) {
                    Surface(
                        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.spacing_medium_8)),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(CoreUiR.dimen.spacing_medium_8),
                                vertical = dimensionResource(CoreUiR.dimen.padding_4)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_4))
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = stringResource(R.string.rating_description),
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_16))
                            )
                            Text(
                                text = String.format("%.1f", review.rating),
                                style = AppTypography.bt4,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    if (isCurrentUser) {
                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_32))
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_review_description),
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_20))
                            )
                        }
                    }
                }
            }

            if (review.reviewText.isNotBlank()) {
                HorizontalDivider(
                    thickness = dimensionResource(CoreUiR.dimen.padding_2).value.toInt().dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )
                Text(
                    text = review.reviewText,
                    style = AppTypography.bt4,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> stringResource(R.string.just_now)
        diff < 3600_000 -> stringResource(R.string.minutes_ago, diff / 60_000)
        diff < 86400_000 -> stringResource(R.string.hours_ago, diff / 3600_000)
        diff < 604800_000 -> stringResource(R.string.days_ago, diff / 86400_000)
        else -> {
            val date = Date(timestamp)
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemPreview() {
    MovuTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_large_16))
        ) {
            ReviewItem(
                review = Review(
                    userId = "user1",
                    userName = "John Doe",
                    mediaId = 550,
                    rating = 4.5f,
                    reviewText = "This is an amazing movie!",
                    timestamp = System.currentTimeMillis() - 86400000
                ),
                isCurrentUser = false
            )

            ReviewItem(
                review = Review(
                    userId = "currentUser",
                    userName = "You",
                    mediaId = 550,
                    rating = 5.0f,
                    reviewText = "Best movie ever!",
                    timestamp = System.currentTimeMillis() - 3600000
                ),
                isCurrentUser = true
            )
        }
    }
}
