package com.raihan.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun CardNextPrayer(
    modifier: Modifier = Modifier,
    nextPrayerTime: NextPrayerTime,
    content: @Composable (() -> Unit?)? = null
) {
    val nextPrayer = buildAnnotatedString {
        append("${nextPrayerTime.textUntil} menuju ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(nextPrayerTime.textPrayer)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            content?.invoke()
            Text(text = "Shalat selanjutnya", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = nextPrayerTime.textPrayerTime+" WIB", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = nextPrayer, color = Color.White, fontSize = 12.sp)
        }
    }
}