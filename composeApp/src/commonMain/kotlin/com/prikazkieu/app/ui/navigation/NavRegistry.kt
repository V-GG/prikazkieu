package com.prikazkieu.app.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlin.reflect.KClass

object NavRegistry {

    // Add one entry per screen that needs non-default nav chrome.
    // Key: a check against the current NavDestination.
    // Value: the set of capability interfaces that route declares.
    private data class Entry(
        val matches: (NavDestination) -> Boolean,
        val capabilities: Set<KClass<*>>
    )

    private val entries = listOf(
        Entry(
            matches = { it.hasRoute<StoryRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, INoBottomNavScreen::class)
        )
    )

    fun resolve(destination: NavDestination?): NavBarState {
        val caps = destination
            ?.let { dest -> entries.firstOrNull { it.matches(dest) }?.capabilities }
            ?: emptySet()

        return NavBarState(
            showBack = ITopBackNavScreen::class in caps || ITopBackSearchNavScreen::class in caps,
            showSearch = ITopBackNavScreen::class !in caps,
            showBottomBar = INoBottomNavScreen::class !in caps
        )
    }
}
