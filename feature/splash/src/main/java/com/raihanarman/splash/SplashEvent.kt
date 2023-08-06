package com.raihanarman.splash

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
sealed interface SplashEvent{
    object Initial: SplashEvent
    object OnNavigateToMain: SplashEvent
}