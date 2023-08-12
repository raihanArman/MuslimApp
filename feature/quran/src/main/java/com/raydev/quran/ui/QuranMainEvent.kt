package com.raydev.quran.ui

import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
sealed interface QuranMainEvent {
    object Initial: QuranMainEvent
    data class OnClickSurah(val surah: Surah): QuranMainEvent
}