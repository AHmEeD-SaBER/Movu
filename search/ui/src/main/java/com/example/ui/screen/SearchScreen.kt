package com.example.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.CustomTextField
import com.example.core_ui.components.ErrorSection
import com.example.core_ui.theme.AppTypography
import com.example.ui.components.SearchResultCard
import com.example.ui.R
import com.example.ui.components.SearchBar
import com.example.ui.components.SearchEmptyContent
import com.example.ui.components.SearchIdleContent
import com.example.ui.components.SearchResultsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchContract.State,
    onEvent: (SearchContract.Events) -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    // Auto-focus the search field when the screen opens
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomAppBar(
            title = {
                Text(
                    text = stringResource(R.string.search_title),
                    style = AppTypography.h7
                )
            },
            modifier = Modifier.padding(start = dimensionResource(com.example.core_ui.R.dimen.padding_12), top = dimensionResource(com.example.core_ui.R.dimen.padding_8)),
            showNavigation = false
        )
        Spacer(modifier = Modifier.height(dimensionResource(com.example.core_ui.R.dimen.padding_8)))

        SearchBar(
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
                onEvent(SearchContract.Events.SearchQueryChanged(query))
            },
            onClearClick = {
                searchQuery = ""
                onEvent(SearchContract.Events.ClearSearch)
            },
            modifier = Modifier
                .padding(horizontal = dimensionResource(com.example.core_ui.R.dimen.padding_12))
                .focusRequester(focusRequester)
        )

        when (state) {
            is SearchContract.State.Idle -> {
                SearchIdleContent()
            }
            is SearchContract.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchContract.State.Error -> {
                ErrorSection(
                    error = state.error,
                    onEvent = { onEvent(SearchContract.Events.Retry) }
                )
            }
            is SearchContract.State.Success -> {
                SearchResultsList(
                    searchResults = state.searchResults,
                    onItemClick = { item ->
                        onEvent(
                            SearchContract.Events.MediaItemClicked(
                                item.searchItem.id,
                                item.mediaType
                            )
                        )
                    }
                )
            }
            is SearchContract.State.EmptyResults -> {
                SearchEmptyContent()
            }
        }
    }
}
