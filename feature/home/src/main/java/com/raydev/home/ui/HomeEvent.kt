package com.raydev.home.ui

import com.raydev.shared.model.QuranLastRead

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
sealed interface HomeEvent {
    object Initial : HomeEvent
    data class OnNavigateToReadQuran(val lastRead: QuranLastRead) : HomeEvent
}
