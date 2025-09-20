package com.example.ui.components

import android.media.browse.MediaBrowser
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.models.MediaItem
import com.example.domain.models.MediaType
import com.example.ui.R
import com.example.ui.home_screen.HomeContract


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesSection(
    modifier: Modifier = Modifier,
    movies: List<MediaItem>,
    onEvent: (HomeContract.Events) -> Unit = {},
) {
    Column(modifier) {
        ListSectionHeader(
            header = R.string.label_popular_movies,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(com.example.core_ui.R.dimen.spacing_extra_large_24))
        ) {
            items(movies) { movie ->
                MovieCard(
                    modifier = Modifier,
                    movie = movie,
                    onClick = { onEvent(HomeContract.Events.MediaItemClicked(movie.id, MediaType.MOVIE)) },
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    movie: MediaItem,
) {
    VerticalMediaItemCard(
        modifier = modifier,
        mediaItem = movie,
        onItemClicked = { onClick() },
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesSectionPreview() {
    val movies = listOf(
        MediaItem(
            id = 1,
            title = "Movie 1",
            image = "",
            rating = 8.5,
        ),
        MediaItem(
            id = 2,
            title = "Movie 2",
            image = "",
            rating = 7.5,
        ),
        MediaItem(
            id = 3,
            title = "Movie 3",
            image = "",
            rating = 6.5,
        ),
    )
    MoviesSection(
        movies = movies,
    )
}




