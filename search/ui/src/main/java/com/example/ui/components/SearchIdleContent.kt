package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.R
import com.example.core_ui.R as CoreUiR

@Composable
fun SearchIdleContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
            dimensionResource(CoreUiR.dimen.padding_32)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(CoreUiR.raw.searching)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(dimensionResource(CoreUiR.dimen.layout_height_100))
                .width(dimensionResource(CoreUiR.dimen.layout_width_200))
        )
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_16)))
        Text(
            text = stringResource(R.string.search_idle_message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}