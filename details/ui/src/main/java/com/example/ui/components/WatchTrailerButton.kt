package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.components.CustomButton

@Composable
fun WatchTrailerButton(
    modifier: Modifier = Modifier,
    onClick : () -> Unit = { /* TODO: Implement trailer functionality */ }
) {
    CustomButton(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        text = "▶ Watch Trailer",
    )

}