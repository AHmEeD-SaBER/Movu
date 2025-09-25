package com.example.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.base.Routes
import com.example.core_ui.components.GradiantEffect
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.Constants
import com.example.navigation.mainnavigation.BottomNavItem
import com.example.navigation.R
import com.example.core_ui.R as CoreUiR

@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        tonalElevation = dimensionResource(CoreUiR.dimen.elevation_6),
        shadowElevation = dimensionResource(CoreUiR.dimen.elevation_6),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(CoreUiR.dimen.layout_height_80))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = dimensionResource(CoreUiR.dimen.padding_8)),

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            items.forEach { item ->


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(
                            vertical = dimensionResource(CoreUiR.dimen.padding_4),
                            horizontal = dimensionResource(CoreUiR.dimen.padding_8)
                        )

                ) {
                    Box(
                        modifier = Modifier
                            .width(dimensionResource(CoreUiR.dimen.layout_width_56))
                            .clip(RoundedCornerShape(dimensionResource(CoreUiR.dimen.corner_radius_48)))
                            .background(
                                if (item.isSelected)
                                    MaterialTheme.colorScheme.inversePrimary.copy(
                                        red = MaterialTheme.colorScheme.inversePrimary.red * 0.8f,
                                        green = MaterialTheme.colorScheme.inversePrimary.green * 0.8f,
                                        blue = MaterialTheme.colorScheme.inversePrimary.blue * 0.8f,
                                        alpha = Constants.ALPHA_MD
                                    )
                                else
                                    Color.Transparent
                            )
                            .padding(vertical = dimensionResource(CoreUiR.dimen.padding_2))
                            .clickable { onItemClick(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                if (item.isSelected) item.selectedIcon else item.unselectedIcon
                            ),
                            contentDescription = stringResource(item.label),
                            tint = if (item.isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_24))
                        )
                    }

                    Text(
                        text = stringResource(item.label),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = AppTypography.bt7.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = dimensionResource(CoreUiR.dimen.padding_4))
                    )

                }
            }
        }
    }
}


@Preview
@Composable
fun CustomBottomBarPreview() {
    val items = listOf(
        BottomNavItem(
            route = Routes.Home,
            label = R.string.home,
            selectedIcon = CoreUiR.drawable.home_filled,
            unselectedIcon = CoreUiR.drawable.home_outlined
        ),
        BottomNavItem(
            route = Routes.Search,
            label = R.string.search,
            selectedIcon = CoreUiR.drawable.search_filled,
            unselectedIcon = CoreUiR.drawable.search_outlined
        ),
        BottomNavItem(
            route = Routes.WatchList,
            label = R.string.WatchList,
            selectedIcon = CoreUiR.drawable.save_filled,
            unselectedIcon = CoreUiR.drawable.save_outlined
        ),
        BottomNavItem(
            route = Routes.Profile,
            label = R.string.profile,
            selectedIcon = CoreUiR.drawable.user_filled,
            unselectedIcon = CoreUiR.drawable.user_outlined
        )
    )

    CustomBottomBar(items) {}
}
