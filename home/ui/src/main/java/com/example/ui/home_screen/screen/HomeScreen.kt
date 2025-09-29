package com.example.ui.home_screen.screen


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_domain.MediaType
import com.example.core_ui.R
import com.example.core_ui.components.CustomAppBar
import com.example.domain.models.MediaItem
import com.example.core_ui.components.ErrorSection
import com.example.ui.components.MoviesSection
import com.example.ui.components.RandomMovieBox
import com.example.ui.components.TvSection
import com.example.ui.home_screen.HomeContract

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeContract.State,
    onEvent: (HomeContract.Events) -> Unit = {},
) {
    when (state) {
        is HomeContract.State.Error -> {
            Box(modifier = modifier) {
                ErrorSection(
                    error = state.error,
                    onEvent = { onEvent(HomeContract.Events.Retry) },
                )
            }
        }

        is HomeContract.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeContract.State.Success -> {
            rememberScrollState()

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Box(
                    modifier = modifier
                        .height(dimensionResource(R.dimen.layout_height_400))
                        .fillMaxWidth()
                ) {
                    RandomMovieBox(
                        modifier = Modifier,
                        mediaDto = state.randomMovie,
                        onClicked = {
                            onEvent(
                                HomeContract.Events.MediaItemClicked(
                                    state.randomMovie.id,
                                    MediaType.MOVIE
                                )
                            )
                        },
                    )
                    CustomAppBar(
                        modifier = Modifier,
                        showNavigation = false,
                        title = {
                            Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_12))) {
                                Image(
                                    painter = painterResource(R.drawable.movu_logo),
                                    contentDescription = stringResource(R.string.app_logo),
                                    modifier = Modifier
                                        .width(dimensionResource(R.dimen.layout_width_75))
                                        .height(dimensionResource(R.dimen.icon_size_48)),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { onEvent(HomeContract.Events.SearchClicked) },
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_8))
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.search_outlined),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = stringResource(R.string.action_search),
                                    modifier = Modifier

                                        .size(dimensionResource(R.dimen.icon_size_24))
                                )
                            }
                        }
                    )
                }



                Column(
                    modifier = modifier.padding(
                        start = dimensionResource(R.dimen.padding_12)
                    )
                ) {

                    MoviesSection(
                        modifier = Modifier,
                        movies = state.movies,
                        onEvent = onEvent,
                    )

                    TvSection(
                        modifier = Modifier.padding(
                            vertical = dimensionResource(R.dimen.padding_16)
                        ),
                        tvShows = state.tvShows,
                        onEvent = onEvent,
                    )

                    Spacer(
                        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_48))
                    )
                }
            }
        }

        is HomeContract.State.Idle -> {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeContract.State.Success(
            movies = listOf(),
            tvShows = listOf(),
            randomMovie = MediaItem(id = 1, title = "Sample Movie", image = "", rating = 9.8)
        )
    )
}
