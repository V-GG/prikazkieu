package com.prikazkieu.app.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
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
            capabilities = setOf(ITopSearchNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<LibraryRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class, ITopFilterNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<AuthorsRoute>() },
            capabilities = setOf(ITopSearchNavScreen::class)
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
            capabilities = setOf(ITopCloseNavScreen::class, INoBottomNavScreen::class)
        ),
        Entry(
            matches = { it.hasRoute<LatestStoriesRoute>() },
            capabilities = setOf(ITopBackNavScreen::class)
        )
    )

    fun resolve(entry: NavBackStackEntry?): NavBarState {
        val destination = entry?.destination
        val caps = destination
            ?.let { dest -> entries.firstOrNull { it.matches(dest) }?.capabilities }
            ?: emptySet()

        // Author/kingdom story lists carry their subject's name + image +
        // moreInfo url as route args — pull them out here so TopNavBar can
        // show an avatar (instead of the search field) that opens the same
        // "more info" page the removed AuthorCard/KingdomCard info button did.
        val (subjectName, subjectImage, subjectMoreInfo) = when {
            destination?.hasRoute<AuthorStoriesRoute>() == true -> {
                val route = entry.toRoute<AuthorStoriesRoute>()
                Triple(route.authorName, route.authorImage, route.authorMoreInfo)
            }
            destination?.hasRoute<KingdomStoriesRoute>() == true -> {
                val route = entry.toRoute<KingdomStoriesRoute>()
                Triple(route.kingdomName, route.kingdomImage, route.kingdomMoreInfo)
            }
            else -> Triple(null, null, null)
        }

        return NavBarState(
            showTopBar = INoTopNavScreen::class !in caps,
            showBack = ITopBackNavScreen::class in caps,
            showClose = ITopCloseNavScreen::class in caps,
            showSearch = ITopSearchNavScreen::class in caps,
            showPrikazkiLogo = ITopPrikazkiLogoScreen::class in caps,
            showDefault = ITopDefaultToolsScreen::class in caps,
            showFilter = ITopFilterNavScreen::class in caps,
            showBottomBar = INoBottomNavScreen::class !in caps,
            subjectName = subjectName,
            subjectImage = subjectImage,
            subjectMoreInfo = subjectMoreInfo
        )
    }
}
