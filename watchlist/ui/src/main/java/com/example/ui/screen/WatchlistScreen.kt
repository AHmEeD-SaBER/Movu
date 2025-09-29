package com.example.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core_ui.components.ErrorSection
import com.example.ui.WatchlistContract

@Composable
fun WatchlistScreen(modifier: Modifier = Modifier, state: WatchlistContract.State, onEvent: (WatchlistContract.Events) -> Unit) {
    Log.d("WatchlistScreen", "Rendering with state: $state")

    LaunchedEffect(Unit) {
        onEvent(WatchlistContract.Events.LoadData)
    }
    when (state) {
        is WatchlistContract.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WatchlistContract.State.Error -> {
            Box(modifier = modifier) {
                ErrorSection(
                    modifier = modifier,
                    error = state.error,
                    onEvent = { onEvent(WatchlistContract.Events.Retry) },
                )
            }
        }

        is WatchlistContract.State.Success -> {
            WatchlistScreenContent(
                modifier = modifier,
                state = state,
                onEvent = onEvent
            )
        }

        WatchlistContract.State.Idle -> {

        }
    }

}