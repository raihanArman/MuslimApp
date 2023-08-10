package com.raydev.muslim_app.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.raihanarman.dashboard.model.DashboardBottomBarItemType
import com.raihanarman.dashboard.model.destinationList

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Composable
fun DashboardBottomNavigation(
    navController: NavHostController
) {
    val surfaceColor = MaterialTheme.colorScheme.onSurface
    val contentColor = Color.White

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = surfaceColor,
        contentColor = contentColor,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        destinationList.forEach { item ->
            if(!DashboardBottomBarItemType.values().contains(item.type)){
                throw Exception("Unknown item type! Please add this type inside the DashboardBottomBarItemType enum class.")
            }

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    item.icon()
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = Color.Gray.copy(alpha = 0.5f),
                ),
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = if (currentRoute == item.route) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            Color.Gray.copy(alpha = 0.5f)
                        }
                    )
                }
            )
        }
    }

}