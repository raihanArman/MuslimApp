package com.rctiplus.main.dashboard.model

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raihanarman.dashboard.R
import com.raihanarman.dashboard.util.Screen

/**
 * @author Raihan Arman
 * @date 01/02/23
 */

val destinationList = listOf(
    DashboardBottomBarItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_home_24),
                contentDescription = null,
                modifier = androidx.compose.ui.Modifier
                    .size(24.dp),
            )
        },
        type = DashboardBottomBarItemType.HOME,
        label = "Home",
        route = Screen.Home.route
    ),
    DashboardBottomBarItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                contentDescription = null,
                modifier = androidx.compose.ui.Modifier
                    .size(24.dp),
            )
        },
        type = DashboardBottomBarItemType.QURAN,
        label = "Quran",
        route = "quran_main"
    ),
)
