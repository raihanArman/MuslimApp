package com.raydev.shared.model

/**
 * @author Raihan Arman
 * @date 13/08/23
 */
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
    return !this.fajr.isNullOrEmpty() &&
        !this.dhuhr.isNullOrEmpty() &&
        !this.asr.isNullOrEmpty() &&
        !this.maghrib.isNullOrEmpty() &&
        !this.isya.isNullOrEmpty()
}
