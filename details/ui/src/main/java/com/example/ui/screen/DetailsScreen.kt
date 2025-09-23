package com.example.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core_domain.MediaType
import com.example.core_ui.components.ErrorSection
import com.example.ui.DetailsContract

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    state: DetailsContract.State,
    onEvent: (DetailsContract.Events) -> Unit,
    mediaType: MediaType
) {

    when (state) {
        is DetailsContract.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailsContract.State.Error -> {
            Box(modifier = modifier) {
                ErrorSection(
                    modifier = modifier,
                    error = state.error,
                    onEvent = { onEvent(DetailsContract.Events.Retry) },
                )
            }
        }

        is DetailsContract.State.Success -> {
            DetailsScreenContent(
                modifier = modifier,
                details = state.details,
                state = state,
                onEvent = onEvent,
                mediaType = mediaType
            )
        }

        is DetailsContract.State.Idle -> {
            // No-op
        }
    }

}