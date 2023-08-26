package com.raydev.muslim_app.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    val surfaceColor = MaterialTheme.colors.onSurface
    val contentColor = Color.White

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = surfaceColor,
        contentColor = contentColor,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        destinationList.forEach { item ->
            if(!DashboardBottomBarItemType.values().contains(item.type)){
                throw Exception("Unknown item type! Please add this type inside the DashboardBottomBarItemType enum class.")
            }

            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    item.icon()
                },
                alwaysShowLabel = true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray.copy(alpha = 0.5f),
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                        color = if (currentRoute == item.route) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            Color.Gray.copy(alpha = 0.5f)
                        }
                    )
                }
            )
        }
    }

}