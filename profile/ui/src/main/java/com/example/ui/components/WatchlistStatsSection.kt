package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun WatchlistStatsSection(
    moviesCount: Int,
    tvShowsCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_16)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_8))
        ) {
            Text(
                text = stringResource(R.string.watchlist_statistics),
                style = AppTypography.h7,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_4)))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(
                    title = stringResource(R.string.label_movies),
                    count = moviesCount,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(dimensionResource(CoreUiR.dimen.padding_8)))

                StatCard(
                    title = stringResource(R.string.label_tv_shows),
                    count = tvShowsCount,
                    modifier = Modifier.weight(1f)
                )
            }

            StatCard(
                title = stringResource(R.string.label_total),
                count = moviesCount + tvShowsCount,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
