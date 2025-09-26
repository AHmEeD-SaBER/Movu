package com.example.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_domain.MediaType
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.IMDbLogoRating
import com.example.core_ui.components.MediaTitle
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.getFullPosterPath
import com.example.domain.Credits
import com.example.domain.MediaDetails
import com.example.domain.Movie
import com.example.domain.Tv
import com.example.ui.DetailsContract
import com.example.ui.R
import com.example.ui.components.BriefDescriptionSection
import com.example.core_ui.R as CoreUiR
import com.example.ui.components.DetailsPoster
import com.example.ui.components.DetailsSection
import com.example.ui.components.RemoveFromWatchlistConfirmationDialog

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    details: MediaDetails,
    state: DetailsContract.State,
    onEvent: (DetailsContract.Events) -> Unit,
    mediaType: MediaType,
) {

    var backgroundColor by remember { mutableStateOf(Color.Black) }

    // Show confirmation dialog when removing from watchlist
    if (state is DetailsContract.State.Success && state.showRemoveConfirmation) {
        RemoveFromWatchlistConfirmationDialog(
            itemTitle = details.title,
            onConfirm = { onEvent(DetailsContract.Events.ConfirmRemoveFromWatchlist) },
            onDismiss = { onEvent(DetailsContract.Events.DismissRemoveConfirmation) }
        )
    }


    DetailsSection(
        mediaDetails = details,
        onEvent = onEvent,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor.copy(alpha = 0.8f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(CoreUiR.dimen.padding_8)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomAppBar(showNavigation = true, navigationIcon = {
                    IconButton(
                        onClick = { onEvent(DetailsContract.Events.BackButtonClicked) },
                        modifier = Modifier.size(
                            dimensionResource(CoreUiR.dimen.icon_size_48)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }, actions = {
                    val isInWatchlist =
                        if (state is DetailsContract.State.Success) state.isInWatchlist else false
                    val isLoading =
                        if (state is DetailsContract.State.Success) state.watchlistLoading else false

                    IconButton(
                        onClick = { onEvent(DetailsContract.Events.ToggleWatchlist) },
                        modifier = Modifier.size(
                            dimensionResource(CoreUiR.dimen.icon_size_48)
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_32)),
                                painter = painterResource(
                                    if (isInWatchlist) CoreUiR.drawable.save_filled
                                    else CoreUiR.drawable.save_outlined
                                ),
                                contentDescription = stringResource(
                                    if (isInWatchlist) R.string.remove_from_watchlist
                                    else R.string.add_to_watchlist
                                ),
                                tint = if (isInWatchlist) MaterialTheme.colorScheme.primary else Color.White
                            )
                        }
                    }
                })

                Spacer(
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_medium_8))
                )

                DetailsPoster(
                    posterPath = details.image.getFullPosterPath(), onColorExtracted = { color ->
                        backgroundColor = color
                    }
                )

                Spacer(
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_extra_large_24))
                )


                MediaTitle(
                    modifier = Modifier,
                    title = details.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTypography.sh2.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_medium_8))
                )
                IMDbLogoRating(
//                    modifier = Modifier.weight(1f),
                    rating = details.rating,
                    color = backgroundColor.copy(alpha = 0.7f),
                    textColor = Color.White

                )

                Spacer(
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_large_16))
                )
                BriefDescriptionSection(
                    data = when (mediaType) {
                        MediaType.MOVIE -> {
                            val movie = details as Movie
                            mapOf(
                                stringResource(R.string.label_length) to movie.length.toHourMinuteSecondFormat(),
                                stringResource(R.string.label_languages) to movie.languages.first(),
                                stringResource(R.string.label_Rating) to "+${movie.voteCount}"

                            )
                        }

                        MediaType.TV_SHOW -> {
                            val tvShow = details as Tv
                            mapOf(
                                stringResource(R.string.label_episodes) to "${tvShow.numberOfEpisodes}",
                                stringResource(R.string.label_seasons) to "${tvShow.numberOfSeasons}",
                                stringResource(R.string.label_languages) to tvShow.languages.first()
                            )
                        }
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(CoreUiR.dimen.padding_12))
                )
                Spacer(
                    modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_large_16))
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    DetailsScreenContent(
        details = Movie(
            id = 1,
            title = "Sample Movie",
            image = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
            genres = listOf("Action", "Adventure", "Sci-Fi"),
            productionCompanies = listOf(
                com.example.domain.ProductionCompany(
                    id = 1,
                    name = "Sample Production",
                    logoPath = "/sample_logo.png",
                )
            ),
            length = 142,
            rating = 8.5,
            languages = listOf("English"),
            plot = "This is a sample overview of the movie. It provides a brief description of the plot and key elements.",
            credits = Credits(
                cast = emptyList(),
                crew = emptyList()
            ),
            voteCount = 2567,
            trailerLink = ""
        ),
        state = DetailsContract.State.Idle,
        onEvent = { /* Preview - no action needed */ },
        mediaType = MediaType.MOVIE
    )
}

@SuppressLint("DefaultLocale")
fun Int.toHourMinuteSecondFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        String.format("%dh %02dm", hours, minutes)
    } else {
        String.format("%dm", minutes)
    }
}
