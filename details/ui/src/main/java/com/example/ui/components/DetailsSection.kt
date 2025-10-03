package com.example.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.domain.MediaDetails
import com.example.ui.DetailsContract
import com.example.core_ui.R as CoreUiR

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsSection(
    mediaDetails: MediaDetails,
    state: DetailsContract.State,
    onEvent: (DetailsContract.Events) -> Unit,
    sheetPeekHeight: Dp = dimensionResource(CoreUiR.dimen.layout_height_350),
    detailsScreenContent: @Composable () -> Unit

) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_16)),
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            DraggableSheetContent(
                mediaDetails = mediaDetails,
                onEvent = onEvent,
                state = state
            )
        },
        content = {
            detailsScreenContent()
        }
    )
}
