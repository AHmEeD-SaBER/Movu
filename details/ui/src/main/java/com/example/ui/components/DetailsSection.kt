package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.dimensionResource
import com.example.domain.MediaDetails
import com.example.ui.DetailsContract
import com.example.core_ui.R as CoreUiR

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsSection(
    mediaDetails: MediaDetails,
    onEvent: (DetailsContract.Events) -> Unit,
    detailsScreenContent: @Composable () -> Unit // Add parameter for the main content
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = dimensionResource(CoreUiR.dimen.layout_height_350),
        sheetShape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16)),
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            DraggableSheetContent(
                mediaDetails = mediaDetails,
                isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded,
                onEvent = onEvent
            )
        },
        content = {
            detailsScreenContent()
        }
    )
}
