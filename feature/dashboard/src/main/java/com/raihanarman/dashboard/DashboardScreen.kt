package com.raihanarman.dashboard

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
import com.raydev.navigation.Destination
import com.raydev.navigation.composable

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
fun NavGraphBuilder.dashboardNavigation() = run {
    composable(Destination.DashboardScreen){
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
        setupDashboardBottomNavScreens()
    }
}

fun NavGraphBuilder.setupDashboardBottomNavScreens(){
    navigation(
        startDestination = Screen.Home.route,
        route = "dashboardBottomNav"
    ){
        composable(Screen.Home.route){
            Box {
                Text("Ini home")
            }
        }
        composable(Screen.Quran.route){
            Box {
                Text("Ini Quran")
            }
        }
    }
}