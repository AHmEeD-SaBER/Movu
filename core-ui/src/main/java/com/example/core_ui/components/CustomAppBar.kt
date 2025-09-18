package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    showSearchBar: Boolean = false,
    searchBar: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
        )
    },
    showNavigation: Boolean = true
) {
    Column(modifier.background(Color.Transparent)) {
        TopAppBar(
            title = title ?: {},
            actions = actions,
            modifier = Modifier.background(Color.Transparent),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface
            ),
            windowInsets = TopAppBarDefaults.windowInsets,
            navigationIcon = if (showNavigation && navigationIcon != null) {
                navigationIcon
            } else {
                {}
            },
        )

        Spacer(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_8))
                .background(Color.Transparent)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_8))
                .background(Color.Transparent)
        ) {
            if (showSearchBar && searchBar != null) {
                searchBar()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CustomAppBarPreview() {
    CustomAppBar(
        title = {
            androidx.compose.material3.Text(text = "Title")
        },
        showSearchBar = true,
        searchBar = {
            androidx.compose.material3.Text(text = "Search Bar")
        }
    )
}