package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.ui.screen.SearchContract
import com.example.core_ui.R

@Composable
fun SearchResultsList(
    searchResults: List<SearchContract.SearchResultItem>,
    onItemClick: (SearchContract.SearchResultItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16))
    ) {
        items(searchResults) { resultItem ->
            SearchResultCard(
                searchResultItem = resultItem,
                onClick = { onItemClick(resultItem) }
            )
        }
    }
}