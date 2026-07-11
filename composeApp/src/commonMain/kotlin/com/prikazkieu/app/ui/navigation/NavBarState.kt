package com.prikazkieu.app.ui.navigation

data class NavBarState(
    val showTopBar: Boolean = true,
    val showBack: Boolean = false,
    val showClose: Boolean = false,
    val showFilter: Boolean = false,
    val showDefault: Boolean = true,
    val showSearch: Boolean = true,
    val showPrikazkiLogo: Boolean = false,
    val showBottomBar: Boolean = true,
    // Set when the screen is filtered by a single author/kingdom — TopNavBar
    // shows their avatar + name here instead of the search field.
    val subjectName: String? = null,
    val subjectImage: String? = null,
    // "More info" URL for the subject — tapping the avatar/name opens it,
    // taking over the role the removed info button on Author/KingdomCard had.
    val subjectMoreInfo: String? = null
)
