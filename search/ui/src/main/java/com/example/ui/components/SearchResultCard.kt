package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_domain.MediaType
import com.example.core_ui.components.CustomTag
import com.example.core_ui.R as CoreUiR
import com.example.ui.R
import com.example.core_ui.components.IMDbLogoRating
import com.example.core_ui.components.MediaTitle
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.getFullPosterPath
import com.example.ui.screen.SearchContract

@Composable
fun SearchResultCard(
    searchResultItem: SearchContract.SearchResultItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_12))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(dimensionResource(CoreUiR.dimen.padding_12))
                ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = searchResultItem.searchItem.image.getFullPosterPath(),
                placeholder = painterResource(CoreUiR.drawable.image_placeholder),
                contentDescription = searchResultItem.searchItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        width = dimensionResource(CoreUiR.dimen.layout_width_80),
                        height = dimensionResource(CoreUiR.dimen.layout_height_120)
                    )
                    .clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_8)))
            )

            Spacer(modifier = Modifier.width(dimensionResource(CoreUiR.dimen.padding_12)))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                MediaTitle(
                    title = searchResultItem.searchItem.title,
                    style = AppTypography.bt2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_12)))


                Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CustomTag(
                        text = when (searchResultItem.mediaType) {
                            MediaType.MOVIE -> stringResource(R.string.label_movie)
                            MediaType.TV_SHOW -> stringResource(R.string.label_tv_show)
                        },
                        modifier = modifier,
                        style = AppTypography.bt4
                    )



                    IMDbLogoRating(
                        rating = searchResultItem.searchItem.rating,
                    )
                }
            }
        }
    }
}
