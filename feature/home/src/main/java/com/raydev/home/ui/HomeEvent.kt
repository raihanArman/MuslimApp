package com.raydev.home.ui

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
sealed interface HomeEvent {
    object Initial: HomeEvent
}