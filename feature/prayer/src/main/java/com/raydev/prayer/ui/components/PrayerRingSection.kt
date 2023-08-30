package com.raydev.prayer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raihan.ui.tile.TilePrayer
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 25/08/23
 */
@Composable
fun PrayerRingSection(
    prayerTime: PrayerTime,
    modifier: Modifier = Modifier,
    subuhIcon: @Composable () -> Unit,
    dhuhurIcon: @Composable () -> Unit,
    asharIcon: @Composable () -> Unit,
    maghribIcon: @Composable () -> Unit,
    isyaIcon: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TilePrayer(textPrayer = "Subuh", textPrayerTime = prayerTime.fajr ?: "") {
            subuhIcon.invoke()
        }
        TilePrayer(textPrayer = "Dhuhur", textPrayerTime = prayerTime.dhuhr ?: "") {
            dhuhurIcon.invoke()
        }
        TilePrayer(textPrayer = "Ashar", textPrayerTime = prayerTime.asr ?: "") {
            asharIcon.invoke()
        }
        TilePrayer(textPrayer = "Maghrib", textPrayerTime = prayerTime.maghrib ?: "") {
            maghribIcon.invoke()
        }
        TilePrayer(textPrayer = "Isya", textPrayerTime = prayerTime.isya ?: "") {
            isyaIcon.invoke()
        }
    }
}
