package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.ui.R
import com.example.core_ui.R as CoreUiR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailsPoster(
    modifier: Modifier = Modifier,
    posterPath: String,
    onColorExtracted: (Color) -> Unit = {}
) {

    val context = LocalContext.current

    LaunchedEffect(posterPath) {
        withContext(Dispatchers.IO) {
            val request = ImageRequest.Builder(context)
                .data(posterPath)
                .allowHardware(false) // Disable hardware acceleration to allow pixel access
                .build()

            val result = context.imageLoader.execute(request)
            if (result is SuccessResult) {
                val bitmap = (result.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
                bitmap?.let {
                    val palette = Palette.from(it).generate()
                    val dominantColor = palette.getDominantColor(android.graphics.Color.BLACK)
                    onColorExtracted(Color(dominantColor))
                }
            }
        }
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(CoreUiR.dimen.elevation_12),

        )
    ) {
        AsyncImage(
            model = posterPath,
            contentDescription = stringResource(R.string.poster_description),
            modifier = Modifier
                .height(dimensionResource(CoreUiR.dimen.layout_height_280))
                .width(dimensionResource(CoreUiR.dimen.layout_width_180)),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(CoreUiR.drawable.image_placeholder)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DetailsPosterPreview() {
    DetailsPoster(
        posterPath = "https://image.tmdb.org/t/p/w500/hZkgoQYus5vegHoetLkCJzb17zJ.jpg"
    )
}
//             title = mediaItem.title
