package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme
import com.example.domain.Review
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun AddReviewDialog(
    existingReview: Review?,
    onDismiss: () -> Unit,
    onSubmit: (Float, String) -> Unit,
    isSubmitting: Boolean = false
) {
    var rating by remember { mutableFloatStateOf(existingReview?.rating ?: 0f) }
    var reviewText by remember { mutableStateOf(existingReview?.reviewText ?: "") }
    val isUpdate = existingReview != null

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.tertiary,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
        title = {
            Text(
                text = stringResource(if (isUpdate) R.string.edit_your_review else R.string.add_your_review),
                style = AppTypography.bt2,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_large_16))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.your_rating),
                        style = AppTypography.bt4,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_medium_8)))
                    RatingBar(
                        rating = rating,
                        onRatingChanged = { rating = it }
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_4)))
                    Text(
                        text = if (rating > 0) stringResource(R.string.rating_format, rating) else stringResource(R.string.tap_to_rate),
                        style = AppTypography.bt5,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(CoreUiR.dimen.layout_height_150)),
                    label = { Text(stringResource(R.string.your_review_optional)) },
                    placeholder = { Text(stringResource(R.string.share_thoughts_placeholder)) },
                    maxLines = 6,
                    shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.padding_12)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (rating > 0) {
                        onSubmit(rating, reviewText)
                    }
                },
                enabled = rating > 0 && !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_16)),
                        strokeWidth = dimensionResource(CoreUiR.dimen.padding_2)
                    )
                } else {
                    Text(
                        text = stringResource(if (isUpdate) R.string.update else R.string.submit),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSubmitting
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AddReviewDialogPreview() {
    MovuTheme {
        AddReviewDialog(
            existingReview = null,
            onDismiss = {},
            onSubmit = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditReviewDialogPreview() {
    MovuTheme {
        AddReviewDialog(
            existingReview = Review(
                userId = "user1",
                userName = "John Doe",
                mediaId = 550,
                rating = 4.5f,
                reviewText = "Great movie!",
                timestamp = System.currentTimeMillis()
            ),
            onDismiss = {},
            onSubmit = { _, _ -> }
        )
    }
}
