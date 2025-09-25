package com.example.navigation.mainnavigation

import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.base.Routes
import com.example.navigation.R
import com.example.core_ui.R as CoreR

class BottomNavigationViewModel :
    BaseViewModel<BottomNavigationContract.Event, BottomNavigationContract.State, BottomNavigationContract.Effect>() {
    override fun setInitialState(): BottomNavigationContract.State {
        return BottomNavigationContract.State(
            items = listOf(
                BottomNavItem(
                    route = Routes.Home,
                    label = R.string.home,
                    selectedIcon = CoreR.drawable.home_filled,
                    unselectedIcon = CoreR.drawable.home_outlined,
                    isSelected = true
                ),
                BottomNavItem(
                    route = Routes.Search,
                    label = R.string.search,
                    selectedIcon = CoreR.drawable.search_filled,
                    unselectedIcon = CoreR.drawable.search_outlined
                ),
                BottomNavItem(
                    route = Routes.WatchList,
                    label = R.string.WatchList,
                    selectedIcon = CoreR.drawable.save_filled,
                    unselectedIcon = CoreR.drawable.save_outlined
                ),
                BottomNavItem(
                    route = Routes.Profile,
                    label = R.string.profile,
                    selectedIcon = CoreR.drawable.user_filled,
                    unselectedIcon = CoreR.drawable.user_outlined
                )
            )
        )
    }

    override fun handleEvent(event: BottomNavigationContract.Event) {
        when (event) {
            is BottomNavigationContract.Event.OnTabSelected -> handleOnTapSelected(event.route)
        }
    }

    private fun handleOnTapSelected(route: Any) {
        setState {
            copy(
                items = uiState.value.items.map {
                    it.copy(
                        isSelected = it.route == route
                    )
                }
            )
        }
    }

}