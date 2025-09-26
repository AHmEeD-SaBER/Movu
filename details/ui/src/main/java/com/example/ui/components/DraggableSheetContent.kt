package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R as CoreUiR
import com.example.core_ui.theme.AppTypography
import com.example.domain.MediaDetails
import com.example.ui.DetailsContract
import com.example.ui.R


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DraggableSheetContent(
    mediaDetails: MediaDetails,
    isExpanded: Boolean,
    onEvent: (DetailsContract.Events) -> Unit
) {
    val maxHeight = LocalConfiguration.current.screenHeightDp.dp * 0.78f
    val lazyListState = rememberLazyListState()
    var preloadContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        preloadContent = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = if (isExpanded) maxHeight else dimensionResource(CoreUiR.dimen.layout_height_350))
            .navigationBarsPadding()
    ) {
        if (!isExpanded) {
            CollapsedContent(mediaDetails = mediaDetails, onEvent = onEvent)
        } else if (preloadContent) {
            ExpandedContent(
                mediaDetails = mediaDetails,
                lazyListState = lazyListState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun CollapsedContent(
    mediaDetails: MediaDetails,
    onEvent: (DetailsContract.Events) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(CoreUiR.dimen.layout_height_350))
            .padding(horizontal = dimensionResource(CoreUiR.dimen.padding_16)),
    ) {
        DescriptionSection(
            plot = mediaDetails.plot,
            genres = mediaDetails.genres,
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))

        WatchTrailerButton(
            modifier = Modifier.padding(top = dimensionResource(CoreUiR.dimen.padding_16)),
            onClick = {
                mediaDetails.trailerLink?.let { trailerLink ->
                    onEvent(DetailsContract.Events.WatchTrailer(trailerLink))
                }
            }
        )
    }
}

@Composable
private fun ExpandedContent(
    mediaDetails: MediaDetails,
    lazyListState: LazyListState,
    onEvent: (DetailsContract.Events) -> Unit
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = dimensionResource(CoreUiR.dimen.padding_16))
    ) {
        item() {
            DescriptionSection(
                plot = mediaDetails.plot,
                genres = mediaDetails.genres
            )
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
        }

        if (mediaDetails.credits.cast.isNotEmpty()) {
            item() {
                CastSection(cast = mediaDetails.credits.cast)
                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
            }
        }

        if (mediaDetails.credits.crew.isNotEmpty()) {
            item() {
                CrewSection(crew = mediaDetails.credits.crew)
                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
            }
        }

        if (mediaDetails.productionCompanies.isNotEmpty()) {
            item() {
                ProductionSection(companies = mediaDetails.productionCompanies)
                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
            }
        }

        item() {
            WatchTrailerButton(
                onClick = {
                    mediaDetails.trailerLink?.let { trailerLink ->
                        onEvent(DetailsContract.Events.WatchTrailer(trailerLink))
                    }
                }
            )
        }
    }
}
