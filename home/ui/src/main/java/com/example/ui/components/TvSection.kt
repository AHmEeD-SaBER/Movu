package com.example.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
fun TvSection(
    modifier: Modifier = Modifier,
    tvShows: List<MediaItem>,
    onEvent: (HomeContract.Events) -> Unit = {},
) {
    Column(modifier) {
        ListSectionHeader(
            header = R.string.label_trending_tv_shows,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(com.example.core_ui.R.dimen.spacing_extra_large_24))
        ) {
            items(tvShows) { tvShow ->
                TvCard(
                    modifier = Modifier,
                    tvShow = tvShow,
                    onClick = {
                        onEvent(
                            HomeContract.Events.MediaItemClicked(
                                tvShow.id,
                                MediaType.TV_SHOW
                            )
                        )
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tvShow: MediaItem,
) {
    VerticalMediaItemCard(
        modifier = modifier,
        mediaItem = tvShow,
        onItemClicked = { onClick() },
    )
}

@Preview(showBackground = true)
@Composable
fun TvSectionPreview() {
    TvSection(
        modifier = Modifier,
        tvShows = listOf(
            MediaItem(
                id = 1,
                title = "Breaking Bad",
                image = "https://image.tmdb.org/t/p/w500/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
                rating = 9.5,
            ),
            MediaItem(
                id = 2,
                title = "Game of Thrones",
                image = "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                rating = 9.3,
            ),
            MediaItem(
                id = 3,
                title = "Stranger Things",
                image = "https://image.tmdb.org/t/p/w500/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg",
                rating = 8.8,
            ),
        ),
    )
}