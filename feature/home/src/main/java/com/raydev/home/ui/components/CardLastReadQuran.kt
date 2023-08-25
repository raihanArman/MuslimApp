package com.raydev.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah
import com.raydev.shared.util.SharedFont

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun CardLastReadQuran(
    modifier: Modifier = Modifier,
    surah: Surah? = null,
    ayah: String ?= null,
    sumAyah: Int = 0,
    lastAyah: Int = 0
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = surah?.name ?: "Al fatihah",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ayah ?: "Ayah ke 5",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
                ProgressLinearCustom(
                    progress = 0.6f
                )
            }
            Column {
                Text(
                    text = surah?.caligraphy ?: "",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(SharedFont.surah))
                )
            }
        }
    }
}