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
        // Root / tab screens
        Entry(
            matches = { it.hasRoute<HomeRoute>() },
            capabilities = setOf(ITopPrikazkiLogoScreen::class, ITopDefaultToolsScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<KingdomsRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class, ITopDefaultToolsScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<LibraryRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<AuthorsRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class, ITopDefaultToolsScreen::class)
        ),
        // Sub-screens
        Entry(
            matches = { it.hasRoute<AllStoriesRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<AuthorStoriesRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<KingdomStoriesRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<AlbumStoriesRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<StoryRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, INoBottomNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<ReadStoryRoute>() },
            capabilities = setOf(ITopBackNavScreen::class, INoBottomNavScreen::class)
        )
    )

    fun resolve(destination: NavDestination?): NavBarState {
        val caps = destination
            ?.let { dest -> entries.firstOrNull { it.matches(dest) }?.capabilities }
            ?: emptySet()

        return NavBarState(
            showTopBar = INoTopNavScreen::class !in caps,
            showBack = ITopBackNavScreen::class in caps,
            showSearch = ITopSearchNavScreen::class in caps,
            showPrikazkiLogo = ITopPrikazkiLogoScreen::class in caps,
            showDefault = ITopDefaultToolsScreen::class in caps,
            showFilter = ITopFilterNavScreen::class in caps,
            showBottomBar = INoBottomNavScreen::class !in caps
        )
    }
}
