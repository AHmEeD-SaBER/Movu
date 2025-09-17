package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.MovuTheme

@Composable
fun CustomTag(modifier: Modifier = Modifier, text: String, style: TextStyle = AppTypography.bt9) {

    Box(
        modifier = modifier
    ) {
        // Blurred background layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_8)))
                .background(
                    Color.LightGray.copy(alpha = 0.5f)
                )
                .blur(
                    radius = 16.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
        )

        // Sharp text layer
        Text(
            text = text,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(
                vertical = dimensionResource(R.dimen.padding_8),
                horizontal = dimensionResource(R.dimen.padding_8)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTagPreview() {
    MovuTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.primary
                )
                .padding(24.dp)
        ) {
            CustomTag(text = "Glass Effect")
        }
    }
}