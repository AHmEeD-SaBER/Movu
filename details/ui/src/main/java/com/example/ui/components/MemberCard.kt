package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_ui.R as CoreUiR
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.getFullPosterPath

@Composable
fun MemberCard(modifier: Modifier = Modifier,profilePath: String, name: String, character: String? = null) {
    Column(
        modifier = modifier.width(dimensionResource(CoreUiR.dimen.layout_width_60)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profilePath.getFullPosterPath())
                .crossfade(true)
                .build(),
            contentDescription = name,
            modifier = Modifier
                .size(dimensionResource(CoreUiR.dimen.icon_size_64))
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(CoreUiR.drawable.person),
            error = painterResource(CoreUiR.drawable.person)
        )

        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.spacing_small_4)))

        Text(
            text = name,
            style = AppTypography.bt7,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        if (!character.isNullOrEmpty())

            Text(
                text = character,
                style = AppTypography.bt9,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
    }
}
