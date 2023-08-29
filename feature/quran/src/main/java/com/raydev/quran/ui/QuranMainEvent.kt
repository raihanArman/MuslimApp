package com.raydev.quran.ui

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
sealed interface QuranMainEvent {
    object Initial : QuranMainEvent
    data class OnClickSurah(val id: Int) : QuranMainEvent
    object OnNavigateToBookmark : QuranMainEvent
    data class OnOpenFilterDialog(val isOpen: Boolean) : QuranMainEvent
}
