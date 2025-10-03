package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R
import com.example.core_ui.theme.MovuTheme

@Composable
fun CustomGradientButton(
    text: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    gradient: Brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.inversePrimary,
            MaterialTheme.colorScheme.inversePrimary,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary,

        )
    ),
    disabledGradient: Brush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
        )
    ),
    content: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.layout_height_48))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_12)))
            .background(
                brush = if (enabled && !isLoading) gradient else disabledGradient
            )
            .clickable(enabled = enabled && !isLoading) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (!isLoading) {
            if (content != null) {
                content()
            } else {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = AppTypography.bt4.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                )
            }
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGradientButtonPreview() {
    MovuTheme {
        CustomGradientButton(
            text = "Next",
            isLoading = false,
            enabled = true,
            onClick = { }
        )
    }
}
