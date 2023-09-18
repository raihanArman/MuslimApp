package com.raydev.prayer.ui

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
sealed interface PrayetEvent {
    object Initial : PrayetEvent
    data class SetRingingSubuh(val value: Int) : PrayetEvent
    data class SetRingingDhuhur(val value: Int) : PrayetEvent
    data class SetRingingAshar(val value: Int) : PrayetEvent
    data class SetRingingMaghrib(val value: Int) : PrayetEvent
    data class SetRingingIsya(val value: Int) : PrayetEvent
    data class OnOpenQiblahDialog(val isOpen: Boolean) : PrayetEvent
}
