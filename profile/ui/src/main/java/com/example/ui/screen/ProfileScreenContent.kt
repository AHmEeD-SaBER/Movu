package com.example.ui.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.CustomButton
import com.example.ui.ProfileContract
import com.example.ui.components.LogoutConfirmationDialog
import com.example.ui.components.ReviewStatsSection
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
            .verticalScroll(scrollState)
            .padding(dimensionResource(CoreUiR.dimen.padding_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_16))
    ) {
        UserInfoSection(user = state.user)

        WatchlistStatsSection(
            moviesCount = state.watchlistCounts.first,
            tvShowsCount = state.watchlistCounts.second
        )

        ReviewStatsSection(
            reviewStatistics = state.reviewStatistics
        )

        CustomButton(
            onClick = { onEvent(ProfileContract.Events.LogoutClicked) },
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.logout_button)
        )

        Spacer(modifier = Modifier.height(100.dp))

        if (state.showLogoutConfirmation) {
            LogoutConfirmationDialog(
                onConfirm = { onEvent(ProfileContract.Events.ConfirmLogout) },
                onDismiss = { onEvent(ProfileContract.Events.DismissLogoutConfirmation) }
            )
        }
    }
}