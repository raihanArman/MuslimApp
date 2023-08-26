package com.raydev.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostApp(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        builder = builder,
        route = route
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composable(
    destination: Destination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.fullRoute,
        arguments = arguments,
        deepLinks = deepLinks,
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        content = content
    )
}