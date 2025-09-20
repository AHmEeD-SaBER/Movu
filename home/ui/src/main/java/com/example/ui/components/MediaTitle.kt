package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography

@Composable
fun MediaTitle(
    modifier: Modifier = Modifier,
    title: String,
    style: TextStyle = AppTypography.sh2,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val baseModifier = if (textAlign == TextAlign.Center) {
        modifier.fillMaxWidth()
    } else {
        modifier
    }

    val content = @Composable {
        Text(
            text = title,
            style = style,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = maxLines,
            textAlign = textAlign,
            overflow = overflow
        )
    }

    if (textAlign == TextAlign.Center) {
        Box(
            modifier = baseModifier,
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    } else {
        Box(modifier = baseModifier) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaTitlePreview() {
    MediaTitle(
        title = "A very long media title that should be truncated at some point to avoid overflow issues",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
}