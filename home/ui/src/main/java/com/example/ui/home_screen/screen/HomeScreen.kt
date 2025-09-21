package com.example.ui.home_screen.screen


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.theme.AppTypography
import com.example.ui.components.ErrorSection
import com.example.ui.components.MoviesSection
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

                CustomAppBar(
                    modifier = Modifier,
                    showNavigation = false,
                    title = {
                        Row {
                            Image(
                                painter = painterResource(R.drawable.m_logo),
                                contentDescription = stringResource(R.string.app_logo),
                                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_64)),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit
                            )
                            Text(
                                text = stringResource(R.string.welcome),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = AppTypography.bt2
                            )
                        }
                    },
                    actions = {
                        Icon(
                            painter = painterResource(R.drawable.search_outlined),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(R.string.action_search),
                            modifier = Modifier.clickable(
                                onClick = { /* TODO: Implement search action */ }
                            )
                        )
                    }
                )


                Column(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.spacing_medium_8),
                        start = dimensionResource(R.dimen.padding_14)
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
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_24))
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
            tvShows = listOf()
        )
    )
}
