package com.raihanarman.read_quran.ui

import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
sealed interface ReadQuranEvent {
    object Initial: ReadQuranEvent
    data class OnClickTabSurah(val id: Int): ReadQuranEvent
    data class OnClickAyah(val surah: Surah, val ayah: Ayah, val ayahIndexSelected: Int): ReadQuranEvent
    object OnBookmarkAyah: ReadQuranEvent
    object OnCloseBottomSheet: ReadQuranEvent
}