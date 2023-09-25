package com.rctiplus.main.dashboard.util

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
sealed class Screen(
    val route: String
) {
    object Home : Screen("home")
    object Quran : Screen("quran")
}
