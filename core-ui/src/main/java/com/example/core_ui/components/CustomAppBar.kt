package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

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
    Column(modifier.background(Color.Transparent).padding(top = dimensionResource(R.dimen.padding_48))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showNavigation && navigationIcon != null) {
                    navigationIcon()
                    Spacer(modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_8)))
                }
                title?.invoke()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions()
            }
        }

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
        showSearchBar = false,
        searchBar = {
            androidx.compose.material3.Text(text = "Search Bar")
        },
        actions = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Action",
            )
        },
        showNavigation = false
    )
}