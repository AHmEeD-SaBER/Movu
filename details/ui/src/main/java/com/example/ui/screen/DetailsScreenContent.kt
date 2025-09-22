package com.example.ui.screen

import android.R.attr.maxWidth
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.IMDbLogoRating
import com.example.core_ui.components.MediaTitle
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.getFullPosterPath
import com.example.domain.Credits
import com.example.domain.MediaDetails
import com.example.domain.Movie
import com.example.ui.DetailsContract
import com.example.ui.R
import com.example.core_ui.R as CoreUiR
import com.example.ui.components.DetailsPoster

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    details: MediaDetails,
    state: DetailsContract.State,
    onEvent: (DetailsContract.Events) -> Unit
) {

    var backgroundColor by remember { mutableStateOf(Color.Black) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor.copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = dimensionResource(CoreUiR.dimen.padding_8)),
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
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(
                        dimensionResource(CoreUiR.dimen.icon_size_48)
                    ),

                    ) {
                    Icon(
                        painter = painterResource(CoreUiR.drawable.save_outlined),
                        contentDescription = stringResource(R.string.back_button_description),
                        tint = Color.White
                    )
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
                modifier = Modifier.size(dimensionResource(CoreUiR.dimen.spacing_extra_x_large_48))
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimensionResource(CoreUiR.dimen.padding_8)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val configuration = LocalConfiguration.current
                val screenWidthDp = configuration.screenWidthDp

                MediaTitle(
                    modifier = Modifier.widthIn(max = (screenWidthDp * 0.75f).dp).weight(1f), // ✅ 80% of screen width
                    title = details.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTypography.sh2.copy(color = Color.White),
                )

                IMDbLogoRating(
//                    modifier = Modifier.weight(1f),
                    rating = details.rating,
                    color = backgroundColor.copy(alpha = 0.7f),
                    textColor = Color.White

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
            productionCompanies = listOf("Marvel Studios", "Disney"),
            length = 142,
            rating = 8.5,
            languages = listOf("English"),
            plot = "This is a sample overview of the movie. It provides a brief description of the plot and key elements.",
            credits = Credits(
                cast = emptyList(),
                crew = emptyList()
            )
        ),
        state = DetailsContract.State.Idle,
        onEvent = { /* Preview - no action needed */ }
    )
}
