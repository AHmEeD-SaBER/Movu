package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R as CoreUiR
import com.example.ui.R

@Composable
fun UserInfoSection(
    user: com.example.domain.models.ProfileDomainUser,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_12)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_8))
        ) {
            Text(
                text = stringResource(R.string.user_information),
                style = AppTypography.h7,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(CoreUiR.dimen.padding_4)))

            InfoRow(label = stringResource(R.string.label_username), value = user.username)
            InfoRow(label = stringResource(R.string.label_email), value = user.email)
            InfoRow(label = stringResource(R.string.label_id), value = user.id)
            InfoRow(
                label = stringResource(R.string.label_account_created),
                value = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
                    .format(java.util.Date(user.createdAt))
            )
        }
    }
}