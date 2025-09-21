package com.example.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.core_domain.MediaType
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography
import com.example.domain.models.MediaItem
import com.example.ui.home_screen.HomeContract
import com.example.ui.utils.getFullPosterPath

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RandomMovieBox(
    modifier: Modifier = Modifier,
    mediaDto: MediaItem,
    onClicked: (HomeContract.Events) -> Unit,

    ) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onClicked(
                    HomeContract.Events.MediaItemClicked(
                        mediaDto.id,
                        mediaType = MediaType.MOVIE
                    )
                )

            }) {
        AsyncImage(
            model = mediaDto.horizontalImage?.getFullPosterPath(),
            contentDescription = mediaDto.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        GradiantEffect()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(R.dimen.padding_16))
        ) {
            MediaTitle(
                title = mediaDto.title,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_16))
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = AppTypography.sh1
            )
        }
    }
}
