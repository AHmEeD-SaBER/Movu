package com.example.core_ui.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.theme.MovuTheme

@Composable
fun PageLayout(
    modifier: Modifier = Modifier,
    bottomContent: @Composable () -> Unit,
    topContent: @Composable () -> Unit = {}
) {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)

        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.6f ),
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.inversePrimary,
                                MaterialTheme.colorScheme.primary,
                            ),
                            start = Offset(Float.POSITIVE_INFINITY, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )
            Column(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = dimensionResource(R.dimen.padding_32)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    topContent()
                }
                Box(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_24))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.movu_logo),
                        contentDescription = stringResource(R.string.app_logo),
                        modifier = Modifier
                            .height(dimensionResource(R.dimen.layout_height_100))
                            .width(dimensionResource(R.dimen.layout_width_150))
                    )
                }
            }


            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                bottomContent()
            }
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = dimensionResource(R.dimen.padding_12))
                    .align(Alignment.TopEnd)
            ) {
                val context = LocalContext.current
                val activity = context as? ComponentActivity
            }
        }
    }
}

@Preview
@Composable
fun PageLayoutPreview() {
    MovuTheme {
        PageLayout(
            bottomContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_16)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to the App",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))
                    Text(
                        text = "Enjoy your experience!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        )
    }
}
