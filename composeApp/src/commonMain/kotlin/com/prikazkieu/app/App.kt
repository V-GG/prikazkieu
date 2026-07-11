package com.prikazkieu.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.prikazkieu.app.ui.navigation.*
import com.prikazkieu.app.ui.screen.search.SearchScreen
import com.prikazkieu.app.ui.screen.splash.SplashScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentDest = currentEntry?.destination

    var showSplash by remember { mutableStateOf(true) }
    var showSearch by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showingSuggestions by remember { mutableStateOf(false) }
    var dismissSuggestions by remember { mutableStateOf({}) }

    val prevIsReadStory = navController.previousBackStackEntry?.destination?.hasRoute<ReadStoryRoute>() == true
    val navBarState = NavRegistry.resolve(currentEntry).let { base ->
        if (currentDest?.hasRoute<ReadStoryRoute>() == true) {
            base.copy(showBack = showingSuggestions || prevIsReadStory)
        } else {
            base
        }
    }

    LaunchedEffect(currentDest) { showFilterSheet = false }

    val selectedItem = when {
        currentDest?.hasRoute<HomeRoute>() == true -> BottomNavItem.Type.HOME.ordinal
        currentDest?.hasRoute<KingdomsRoute>() == true -> BottomNavItem.Type.KINGDOMS.ordinal
        currentDest?.hasRoute<LibraryRoute>() == true -> BottomNavItem.Type.LIBRARY.ordinal
        currentDest?.hasRoute<AuthorsRoute>() == true -> BottomNavItem.Type.AUTHORS.ordinal
        else -> BottomNavItem.Type.HOME.ordinal
    }

    val tabNavOptions = navOptions {
        popUpTo<HomeRoute> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    topBar = {
                        if (navBarState.showTopBar) {
                            TopNavBar(
                                state = navBarState,
                                onBack = {
                                    if (showingSuggestions) dismissSuggestions() else navController.popBackStack()
                                },
                                onClose = {
                                    while (navController.currentDestination?.hasRoute<ReadStoryRoute>() == true) {
                                        navController.popBackStack()
                                    }
                                },
                                onBlogClick = { navController.navigate(StoryRoute("$BASE_URL/blog/")) },
                                onSearchClick = { showSearch = true },
                            onFilterClick = { showFilterSheet = true },
                            onLatestClick = { navController.navigate(LatestStoriesRoute) },
                            onSubjectClick = { url -> navController.navigate(StoryRoute(url)) }
                            )
                        }
                    },
                    bottomBar = {
                        if (navBarState.showBottomBar) {
                            BottomNavBar(selectedItem) { newItem ->
                                when (newItem) {
                                    BottomNavItem.Type.HOME.ordinal -> navController.navigate(HomeRoute, tabNavOptions)
                                    BottomNavItem.Type.KINGDOMS.ordinal -> navController.navigate(KingdomsRoute, tabNavOptions)
                                    BottomNavItem.Type.LIBRARY.ordinal -> navController.navigate(LibraryRoute, tabNavOptions)
                                    BottomNavItem.Type.AUTHORS.ordinal -> navController.navigate(AuthorsRoute, tabNavOptions)
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = if (navBarState.showTopBar) 110.dp else 0.dp,
                                bottom = paddingValues.calculateBottomPadding()
                            )
                    ) {
                        Navigation(
                            navController = navController,
                            showFilterSheet = showFilterSheet,
                            onFilterDismiss = { showFilterSheet = false },
                            onReadingNavStateChanged = { isShowingSuggestions, onDismissSuggestions ->
                                showingSuggestions = isShowingSuggestions
                                dismissSuggestions = onDismissSuggestions
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = showSearch,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    SearchScreen(
                        onClose = { showSearch = false },
                        onStoryClick = { story ->
                            showSearch = false
                            navController.navigate(ReadStoryRoute(story))
                        },
                        onAuthorClick = { author ->
                            showSearch = false
                            navController.navigate(AuthorStoriesRoute(author.name, author.image, author.moreInfo))
                        },
                        onKingdomClick = { kingdom ->
                            showSearch = false
                            navController.navigate(KingdomStoriesRoute(kingdom.name, kingdom.image, kingdom.moreInfo))
                        }
                    )
                }

                if (showSplash) {
                    SplashScreen(onFinished = { showSplash = false })
                }
            }
        }
    }
}