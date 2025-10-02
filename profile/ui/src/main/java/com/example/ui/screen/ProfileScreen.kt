package com.example.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.ErrorSection
import com.example.core_ui.theme.AppTypography
import com.example.ui.ProfileContract
import com.example.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileContract.State,
    onEvent: (ProfileContract.Events) -> Unit,
    modifier: Modifier = Modifier
) {

    Column {
        CustomAppBar(
            title = {
                Text(
                    text = stringResource(R.string.label_profile),
                    style = AppTypography.h7
                )
            },
            modifier = Modifier.padding(start = dimensionResource(com.example.core_ui.R.dimen.padding_12), top = dimensionResource(com.example.core_ui.R.dimen.padding_8)),
            showNavigation = false
        )

        when (state) {
            ProfileContract.State.Loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            is ProfileContract.State.Error -> {
                ErrorSection(
                    error = state.error,
                    onEvent = { onEvent(ProfileContract.Events.Retry) },
                    modifier = modifier.fillMaxSize()
                )
            }

            is ProfileContract.State.Success -> {
                ProfileScreenContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = modifier
                )
            }

            ProfileContract.State.Idle -> {
            }
        }
    }
}











