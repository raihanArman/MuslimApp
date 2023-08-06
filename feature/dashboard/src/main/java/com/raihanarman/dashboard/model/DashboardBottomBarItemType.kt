package com.raihanarman.dashboard.model

import androidx.compose.runtime.Composable

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
enum class DashboardBottomBarItemType {
    HOME, QURAN
}

data class DashboardBottomBarItem(
    val icon: @Composable () -> Unit,
    val label: String,
    val type: DashboardBottomBarItemType,
    val route: String,
)