package com.example.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import com.example.core_ui.R
import com.example.core_ui.theme.MovuTheme
import com.example.ui.SplashContract

@Composable
fun SplashScreen(
    state: SplashContract.State,
    onEvent: (SplashContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(SplashContract.Event.CheckAuthStatus)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimensionResource(R.dimen.padding_24)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            painter = painterResource(R.drawable.movu_logo),
            contentDescription = stringResource(R.string.app_logo),
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium_8)))

        if (state.isLoading) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.loader)
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MovuTheme {
        SplashScreen(
            state = SplashContract.State(
                isLoading = true
            ),
            onEvent = {}
        )
    }
}
