package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.MovuTheme
import com.example.core_ui.R as CoreUiR

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    onRatingChanged: (Float) -> Unit = {}
) {
    var currentRating by remember { mutableFloatStateOf(rating) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_medium_8)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxStars) { index ->
            val starIndex = index + 1
            Icon(
                imageVector = if (starIndex <= currentRating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = "Star $starIndex",
                tint = if (starIndex <= currentRating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(dimensionResource(CoreUiR.dimen.icon_size_32))
                    .clickable {
                        currentRating = starIndex.toFloat()
                        onRatingChanged(currentRating)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingBarPreview() {
    MovuTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.spacing_large_16))
        ) {
            RatingBar(rating = 0f)
            RatingBar(rating = 2.5f)
            RatingBar(rating = 5f)
        }
    }
}
