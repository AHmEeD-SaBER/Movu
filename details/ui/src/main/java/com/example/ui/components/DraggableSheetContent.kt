package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R as CoreUiR
import com.example.domain.MediaDetails
import com.example.ui.DetailsContract


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DraggableSheetContent(
    mediaDetails: MediaDetails,
    onEvent: (DetailsContract.Events) -> Unit,
    state: DetailsContract.State
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        contentPadding = PaddingValues(horizontal = dimensionResource(CoreUiR.dimen.padding_16))
    ) {
        item {
            DescriptionSection(
                plot = mediaDetails.plot,
                genres = mediaDetails.genres
            )
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
        }

        // Review Action Button
        if (state is DetailsContract.State.Success) {
            item {
                val showReviewDialog = remember(onEvent) {
                    { onEvent(DetailsContract.Events.ShowAddReviewDialog) }
                }
                ReviewActionButton(
                    userReview = state.userReview,
                    onClick = showReviewDialog
                )
                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
            }
        }

        // Reviews Section
        if (state is DetailsContract.State.Success) {
            item {
                ReviewsSection(
                    reviews = state.reviews,
                    currentUserId = state.userReview?.userId,
                    isLoading = state.reviewsLoading,
                    onDeleteReview = { onEvent(DetailsContract.Events.DeleteReview) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
            }
        }

        item {
            CastSection(cast = mediaDetails.credits.cast)
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
        }

        item {
            CrewSection(crew = mediaDetails.credits.crew)
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
        }

        item {
            ProductionSection(companies = mediaDetails.productionCompanies)
            Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_extra_large_24)))
        }

        item {
            WatchTrailerButton(
                onClick = {
                    mediaDetails.trailerLink?.let { trailerLink ->
                        onEvent(DetailsContract.Events.WatchTrailer(trailerLink))
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_16)))
        }
    }
}
