package com.raydev.muslim_app.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.raihanarman.dashboard.components.DashboardBottomNavigation
import com.raihanarman.dashboard.util.Screen
import com.raydev.home.ui.homeMainNavigation
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import com.raydev.quran.ui.quranMainNavigation

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
fun NavGraphBuilder.dashboardNavigation() = run {
    composable(Destination.DashboardScreen){
        println("Ampas kuda -> Su| dashboardNavigation")
        DashboardScreen()
    }
}

@Composable
fun DashboardScreen() {
    val dashboardNav = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomNavigation(navController = dashboardNav)
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding())
                ) {
                    DashboardContent(navController = dashboardNav)
                }
            }
        }
    )
}

@Composable
fun DashboardContent(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "dashboardBottomNav"
    ){
        println("Ampas kuda -> Su| NavHost")
        setupDashboardBottomNavScreens()
    }
}

fun NavGraphBuilder.setupDashboardBottomNavScreens(){
    navigation(
        startDestination = Screen.Home.route,
        route = "dashboardBottomNav"
    ){
//        val currentScreen = rememberUpdatedState(newValue = title)
//        val currentRoute = currentRoute(navController)

        println("Ampas kuda -> Su| setupDashboardBottomNavScreens")
        homeMainNavigation()
        quranMainNavigation()
    }
}