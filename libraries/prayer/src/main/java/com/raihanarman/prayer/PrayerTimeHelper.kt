package com.raihanarman.prayer

import android.annotation.SuppressLint
import com.raydev.shared.model.PrayerTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.regex.Pattern

/**
 * @author Raihan Arman
 * @date 13/08/23
 */
@SuppressLint("SimpleDateFormat")
fun PrayerTime.getCurrentPrayerTimeString(): String? {
    val now = SimpleDateFormat("HH:mm").format(java.util.Calendar.getInstance().time)
    return when {
        now < fajr!! -> fajr
        now < dhuhr!! -> dhuhr
        now < asr!! -> asr
        now < maghrib!! -> maghrib
        now < isya!! -> isya
        else -> {
            fajr
        }
    }
}

fun countTimeLight(endTime: String): String {
    if (endTime.isNotEmpty()) {
        val cal = Calendar.getInstance()
        val nowHour = cal.get(Calendar.HOUR_OF_DAY)
        val nowMin = cal.get(Calendar.MINUTE)
        val m = Pattern.compile("(\\d{2}):(\\d{2})").matcher(endTime)
        require(m.matches()) { "Invalid time format: $endTime" }
        val endHour = Integer.parseInt(m.group(1)!!)
        val endMin = Integer.parseInt(m.group(2)!!)
        require(!(endHour >= 24 || endMin >= 60)) { "Invalid time format: $endTime" }
        var minutesLeft = endHour * 60 + endMin - (nowHour * 60 + nowMin)
        if (minutesLeft < 0)
            minutesLeft += 24 * 60 // Time passed, so time until 'end' tomorrow
        val hours = minutesLeft / 60
        val minutes = minutesLeft - hours * 60

        val minutesString =
            if (minutes != 0) "$minutes Menit" else ""

        return "$hours Jam $minutesString"
    } else
        return ""
}

fun PrayerTime.getTimeUntilNextPrayerString(): Pair<String, String> {
    val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
    return when {
        now < fajr!! -> Pair(countTimeLight(fajr!!), "Subuh")
        now < dhuhr!! -> Pair(countTimeLight(dhuhr!!), "Dhuhur")
        now < asr!! -> Pair(countTimeLight(asr!!), "Ashar")
        now < maghrib!! -> Pair(countTimeLight(maghrib!!), "Maghrib")
        now < isya!! -> Pair(countTimeLight(isya!!), "Isya")
        else -> Pair(countTimeLight(fajr!!), "Subuh")
    }
}