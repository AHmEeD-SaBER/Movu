package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R as CoreUiR
import com.example.core_ui.theme.AppTypography
import com.example.domain.CastMember
import com.example.ui.R

@Composable
fun CastSection(cast: List<CastMember>) {
    Column {
        Text(
            text = stringResource(R.string.label_cast),
            style = AppTypography.h7,
            modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_12))
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_16))
        ) {
            items(cast) { castMember ->
                MemberCard(
                    profilePath = castMember.profilePath ?: "",
                    name = castMember.name,
                    character = castMember.character
                )
            }
        }
    }
}