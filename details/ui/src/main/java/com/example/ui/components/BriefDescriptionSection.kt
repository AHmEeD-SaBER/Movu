package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BriefDescriptionSection(modifier: Modifier = Modifier, data: Map<String, String>) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        data.entries.forEachIndexed { index, (key, value) ->
            val alignment = when (index) {
                0 -> Alignment.Start
                data.size - 1 -> Alignment.End
                else -> Alignment.CenterHorizontally
            }

            DataSection(
                modifier = Modifier.weight(1f),
                topText = key,
                bottomText = value,
                alignment = alignment
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BriefDescriptionSectionPreview() {
    BriefDescriptionSection(
        data = mapOf(
            "Release Date" to "2023-10-10",
            "Duration" to "2h 30m",
            "Rating" to "PG-13"
        )
    )
}