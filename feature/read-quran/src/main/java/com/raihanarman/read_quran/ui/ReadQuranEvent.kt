package com.raihanarman.read_quran.ui

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
sealed interface ReadQuranEvent {
    object Initial: ReadQuranEvent
    data class OnClickTabSurah(val id: Int): ReadQuranEvent
}