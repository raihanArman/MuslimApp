package com.raydev.prayer.ui

import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
data class PrayerState(
    val prayerTime: PrayerTime ? = null,
    val nextPrayerTime: NextPrayerTime ? = null,
    val subuhRing: Boolean = false,
    val dhuhurRing: Boolean = false,
    val asharRing: Boolean = false,
    val maghribRing: Boolean = false,
    val isyaRing: Boolean = false,
    val isOpenQiblahDialog: Boolean = false,
    val userLatitude: Double = 0.0
)
