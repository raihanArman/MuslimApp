package com.raydev.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.ui.badge.BadgeLocation
import com.raihan.ui.card.CardNextPrayer
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun CardMainInformation(
    modifier: Modifier = Modifier,
    prayerTime: PrayerTime,
    nextPrayerTime: NextPrayerTime,
    hijrDate: String,
    currentDate: String
) {
    CardNextPrayer(
        modifier = modifier,
        nextPrayerTime = nextPrayerTime,
    ) {
        Row {
            Spacer(modifier = Modifier.height(15.dp))
            BadgeLocation(location = prayerTime.address.orEmpty())
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = hijrDate,
                    color = Color.White,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = currentDate,
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
    }
}