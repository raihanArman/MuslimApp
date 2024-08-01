package com.raydev.shared.model

import androidx.compose.runtime.Immutable

/**
 * @author Raihan Arman
 * @date 13/08/23
 */
@Immutable
data class PrayerTime(
    var address: String? = null,
    var fajr: String? = "",
    var sunrise: String? = "",
    var dhuhr: String? = "",
    var asr: String? = "",
    var sunset: String? = "",
    var maghrib: String? = "",
    var isya: String? = ""
)

fun PrayerTime.checkPrayerTimeIsNotEmpty(): Boolean {
    val defaultValue = "00:00"
    return !this.fajr.isNullOrEmpty() && !fajr.equals(defaultValue) &&
        !this.dhuhr.isNullOrEmpty() && !dhuhr.equals(defaultValue) &&
        !this.asr.isNullOrEmpty() && !asr.equals(defaultValue) &&
        !this.maghrib.isNullOrEmpty() && !maghrib.equals(defaultValue) &&
        !this.isya.isNullOrEmpty() && !isya.equals(defaultValue)
}
