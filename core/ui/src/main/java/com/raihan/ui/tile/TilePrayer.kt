package com.raihan.ui.tile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 25/08/23
 */
@Composable
fun TilePrayer(
    textPrayer: String,
    textPrayerTime: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        color = Color.Gray.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
        ) {
            Text(text = textPrayer, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = textPrayerTime, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            icon.invoke()
        }
    }
}