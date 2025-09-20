package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.ui.R
import com.example.ui.utils.roundToOneDecimal
import com.example.core_ui.R as CoreUiR

@Composable
fun IMDbLogoRating(modifier: Modifier = Modifier, rating: Double = 0.0) {
    Box(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            shape = RoundedCornerShape(
                dimensionResource(CoreUiR.dimen.corner_radius_12)
            )
        ).padding(dimensionResource(CoreUiR.dimen.padding_4))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_4))
        ) {
            Image(
                painter = painterResource(R.drawable.imdb_logo_2016),
                contentDescription = "IMDb Logo",
                modifier = Modifier
                    .width(dimensionResource(CoreUiR.dimen.layout_width_32))
                    .height(dimensionResource(CoreUiR.dimen.layout_height_17)),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = rating.roundToOneDecimal(),
                style = AppTypography.sh7,
            )
        }
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000)
@Composable
fun IMDbLogoRatingPreview() {
    IMDbLogoRating(rating = 8.5)
}