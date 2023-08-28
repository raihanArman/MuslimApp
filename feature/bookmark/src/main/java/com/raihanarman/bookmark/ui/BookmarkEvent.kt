package com.raihanarman.bookmark.ui

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
sealed interface BookmarkEvent {
    object Initial : BookmarkEvent
    object OnBack : BookmarkEvent
    data class OnNavigateToReadQuran(val surahId: Int, val ayahId: Int) : BookmarkEvent
}
