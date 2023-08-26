package com.raihanarman.bookmark.ui

import com.raydev.shared.model.Ayah

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
sealed interface BookmarkEvent {
    object Initial: BookmarkEvent
    object OnBack: BookmarkEvent
    data class OnNavigateToReadQuran(val surahId: Int, val ayahId: Int): BookmarkEvent
}