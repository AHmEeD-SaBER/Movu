package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomGradientButton
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme
import com.example.domain.Review
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun ReviewActionButton(
    userReview: Review?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomGradientButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8))
            ) {
                Icon(
                    imageVector = if (userReview != null) Icons.Default.Edit else Icons.Default.Add,
                    contentDescription = if (userReview != null) {
                        stringResource(R.string.edit_your_review)
                    } else {
                        stringResource(R.string.add_your_review)
                    },
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_20))
                )
                Text(
                    text = if (userReview != null) {
                        stringResource(R.string.edit_your_review)
                    } else {
                        stringResource(R.string.add_your_review)
                    },
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = AppTypography.bt4.copy(fontWeight = FontWeight.Bold),
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewActionButtonPreview() {
    MovuTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_large_16))
        ) {
            ReviewActionButton(
                userReview = null,
                onClick = {}
            )

            ReviewActionButton(
                userReview = Review(
                    userId = "user1",
                    userName = "John Doe",
                    mediaId = 550,
                    rating = 4.5f,
                    reviewText = "Great movie!",
                    timestamp = System.currentTimeMillis()
                ),
                onClick = {}
            )
        }
    }
}
