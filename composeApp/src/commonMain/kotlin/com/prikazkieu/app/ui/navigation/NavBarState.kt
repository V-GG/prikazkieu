package com.prikazkieu.app.ui.navigation

data class NavBarState(
    val showTopBar: Boolean = true,
    val showBack: Boolean = false,
    val showClose: Boolean = false,
    val showFilter: Boolean = false,
    val showDefault: Boolean = true,
    val showSearch: Boolean = true,
    val showPrikazkiLogo: Boolean = false,
    val showBottomBar: Boolean = true
)
