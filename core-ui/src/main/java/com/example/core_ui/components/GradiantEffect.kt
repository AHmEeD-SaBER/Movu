package com.example.core_ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GradiantEffect(
    modifier: Modifier = Modifier,
    color1: Color = Color.Transparent,
    color2: Color = Color.Black,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(
                            color1,
                            color1,
                            color2,
                        ),
                    )
            )
    )
}

@Preview(showBackground = true)
@Composable
fun GradiantEffectPreview() {
    GradiantEffect()
}