package com.example.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core_domain.CustomError
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    error: CustomError,
    onEvent: () -> Unit,
    drawableRes: Int? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (drawableRes != null)
            Image(
                painter = painterResource(drawableRes),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.layout_height_250))
            )
        Text(
            text = stringResource(error.titleRes),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)),
            style = AppTypography.bt2,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(error.subtitleRes),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_4)),
            style = AppTypography.bt2,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onEvent() },
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_16))
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = AppTypography.bt2,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}