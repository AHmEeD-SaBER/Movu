package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography

@Composable
fun DataSection(
    modifier: Modifier = Modifier,
    topText: String,
    bottomText: String,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = alignment, // Use the passed alignment parameter
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = topText,
            style = AppTypography.bt4.copy(color = Color.White.copy(alpha = 0.5f)),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_8))
        )

        Spacer(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_4))
        )

        Text(
            text = bottomText,
            style = AppTypography.bt4.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_8))
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun DataSectionPreview() {
    DataSection(
        topText = "Release Date",
        bottomText = "2023-10-10"
    )
}