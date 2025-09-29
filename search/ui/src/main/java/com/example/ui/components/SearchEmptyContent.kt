package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun SearchEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                dimensionResource(CoreUiR.dimen.padding_32)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.search_no_results_title),
            style = AppTypography.h7,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_8)))
        Text(
            text = stringResource(R.string.search_no_results_message),
            style = AppTypography.bt4,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}