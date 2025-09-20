package com.example.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R as CoreUiR
import com.example.domain.models.MediaItem
import com.example.ui.R
import coil.compose.AsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun VerticalMediaItemCard(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem,
    onItemClicked: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .height(dimensionResource(CoreUiR.dimen.layout_height_270))
            .width(dimensionResource(CoreUiR.dimen.layout_width_180))
            .clickable { onItemClicked(mediaItem.id) }
            .clip(
                RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16))
            )
    ) {
        AsyncImage(
            model = mediaItem.image,
            placeholder = painterResource(R.drawable.image_placeholder),
            contentDescription = mediaItem.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        IMDbLogoRating(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(dimensionResource(CoreUiR.dimen.padding_6)),
            rating = mediaItem.rating
        )

        GradiantEffect()

        MediaTitle(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    horizontal = dimensionResource(CoreUiR.dimen.padding_6),
                    vertical = dimensionResource(CoreUiR.dimen.padding_12)
                ),
            title = mediaItem.title,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = AppTypography.bt7,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalMediaItemCardPreview() {
    VerticalMediaItemCard(
        modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_16)),
        mediaItem = MediaItem(
            id = 1,
            title = "Movie Title",
            image = "",
            rating = 8.5
        ),
        onItemClicked = {}
    )
}
