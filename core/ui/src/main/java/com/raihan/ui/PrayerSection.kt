package com.raihan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raihan.ui.card.CardPrayer
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun PrayerSection(
    modifier: Modifier = Modifier,
    prayerTime: PrayerTime,
    nextPrayerTime: NextPrayerTime
) {
    val nextPrayer = nextPrayerTime.textPrayerTime
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CardPrayer(
            time = "Subuh",
            prayer = prayerTime.fajr.orEmpty(),
            isSelected = prayerTime.fajr?.equals(nextPrayer) == true
        )
        CardPrayer(
            time = "Dhuhur",
            prayer = prayerTime.dhuhr.orEmpty(),
            isSelected = prayerTime.dhuhr?.equals(nextPrayer) == true
        )
        CardPrayer(
            time = "Ashar",
            prayer = prayerTime.asr.orEmpty(),
            isSelected = prayerTime.asr?.equals(nextPrayer) == true
        )
        CardPrayer(
            time = "Maghrib",
            prayer = prayerTime.maghrib.orEmpty(),
            isSelected = prayerTime.maghrib?.equals(nextPrayer) == true
        )
        CardPrayer(
            time = "Isya",
            prayer = prayerTime.isya.orEmpty(),
            isSelected = prayerTime.isya?.equals(nextPrayer) == true
        )
    }
}
