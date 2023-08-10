package com.raydev.muslim_app

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.raihanarman.splash.splashNavigation
import com.raydev.muslim_app.dashboard.dashboardNavigation
import com.raydev.navigation.Destination
import com.raydev.navigation.NavHostApp
import com.raydev.navigation.NavigationIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {
    val navController = rememberAnimatedNavController()
    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController
    )
    NavHostApp(
        navController = navController,
        startDestination = Destination.SplashScreen
    ) {
        splashNavigation()
        dashboardNavigation()
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true){
                return@collect
            }
            when(intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route!!, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute){
                                inclusive = intent.inclusive
                            }
                        }
                    }
                }
                is NavigationIntent.NavigateAndReplace -> {
                    navHostController.apply {
                        popBackStack(graph.startDestinationId, true)
                        graph.setStartDestination(intent.route)
                        navigate(intent.route)
                    }
                }
            }
        }
    }
}