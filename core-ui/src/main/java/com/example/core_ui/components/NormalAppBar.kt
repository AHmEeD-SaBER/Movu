package com.example.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R as CoreR

@Composable
fun NormalAppBar() {
    CustomAppBar(
        modifier = Modifier,
        showNavigation = false,
        title = {
            Column(modifier = Modifier.padding(start = dimensionResource(CoreR.dimen.padding_12))) {
                Image(
                    painter = painterResource(CoreR.drawable.movu_logo),
                    contentDescription = stringResource(CoreR.string.app_logo),
                    modifier = Modifier
                        .width(dimensionResource(CoreR.dimen.layout_width_75))
                        .height(dimensionResource(CoreR.dimen.icon_size_48)),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
            }
        },
        actions = {
            Icon(
                painter = painterResource(CoreR.drawable.search_outlined),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(CoreR.string.action_search),
                modifier = Modifier
                    .clickable(onClick = { /* TODO: Implement search action */ })
                    .padding(end = dimensionResource(CoreR.dimen.padding_12))
                    .size(dimensionResource(CoreR.dimen.icon_size_24))
            )
        }
    )
}