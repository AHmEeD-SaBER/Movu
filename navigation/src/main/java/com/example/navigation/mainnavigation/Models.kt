package com.example.navigation.mainnavigation

data class BottomNavItem(
    val route: Any,
    val label: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val isSelected: Boolean = false
)