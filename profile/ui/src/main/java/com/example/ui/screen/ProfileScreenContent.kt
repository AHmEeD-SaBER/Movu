package com.example.ui.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.components.CustomButton
import com.example.ui.ProfileContract
import com.example.ui.components.LogoutConfirmationDialog
import com.example.ui.components.UserInfoSection
import com.example.ui.components.WatchlistStatsSection
import com.example.core_ui.R as CoreUiR
import com.example.ui.R

@Composable
fun ProfileScreenContent(
    state: ProfileContract.State.Success,
    onEvent: (ProfileContract.Events) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .scrollable(state = scrollState, orientation = androidx.compose.foundation.gestures.Orientation.Vertical)
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(CoreUiR.dimen.padding_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_16))
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_8)))
        UserInfoSection(user = state.user)

        Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_8)))

        WatchlistStatsSection(
            moviesCount = state.watchlistCounts.first,
            tvShowsCount = state.watchlistCounts.second
        )
        Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_8)))

        CustomButton(
            onClick = { onEvent(ProfileContract.Events.LogoutClicked) },
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.logout_button)
        )

        if (state.showLogoutConfirmation) {
            LogoutConfirmationDialog(
                onConfirm = { onEvent(ProfileContract.Events.ConfirmLogout) },
                onDismiss = { onEvent(ProfileContract.Events.DismissLogoutConfirmation) }
            )
        }
    }
}