package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography


@Composable
fun ListSectionHeader(
    modifier: Modifier = Modifier,
    header: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_12)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(header),
            modifier = Modifier.weight(1f),
            style = AppTypography.sh2,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListSectionHeaderPreview() {
    ListSectionHeader(
        header = com.example.ui.R.string.label_popular_movies
    )
}