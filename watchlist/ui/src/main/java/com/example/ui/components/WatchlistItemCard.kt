package com.example.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_ui.components.IMDbLogoRating
import com.example.core_ui.utils.getFullPosterPath
import com.example.domain.models.WatchlistItem
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchlistItemCard(
    modifier: Modifier = Modifier,
    mediaItem: WatchlistItem,
    isSelectionMode: Boolean = false,
    isSelected: Boolean = false,
    onItemClicked: (Int) -> Unit,
    onItemLongClicked: (Int) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f)
            .combinedClickable(
                onClick = { onItemClicked(mediaItem.id) },
                onLongClick = { onItemLongClicked(mediaItem.id) }
            )
            .clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16)))
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16))
                    )
                } else Modifier
            )
    ) {
        AsyncImage(
            model = mediaItem.image.getFullPosterPath(),
            placeholder = painterResource(CoreUiR.drawable.image_placeholder),
            contentDescription = mediaItem.title,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16))),
            contentScale = ContentScale.FillBounds
        )

        if (isSelectionMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSelected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else
                            Color.Black.copy(alpha = 0.2f)
                    )
                    .clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16)))
            )
        }

        if (!isSelectionMode) {
            IMDbLogoRating(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(dimensionResource(CoreUiR.dimen.padding_6)),
                rating = mediaItem.rating
            )
        }

        if (isSelectionMode) {
            Icon(
                imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.CheckBoxOutlineBlank,
                contentDescription = if (isSelected) stringResource(R.string.label_selected) else stringResource(R.string.label_not_selected),
                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(CoreUiR.dimen.padding_8))
                    .size(dimensionResource(CoreUiR.dimen.icon_size_24))
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
            )
        }
    }
}