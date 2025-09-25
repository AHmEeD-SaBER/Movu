package com.example.navigation.mainnavigation

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState

class BottomNavigationContract {
    data class State(
        val items: List<BottomNavItem> = emptyList()
    ): UiState

    sealed class Event : UiEvent {
        data class OnTabSelected(val route: Any) : Event()
    }

    sealed class Effect : UiEffect
}