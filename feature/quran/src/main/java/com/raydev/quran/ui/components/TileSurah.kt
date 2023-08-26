package com.raydev.quran.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah
import com.raydev.shared.util.SharedFont
import com.raydev.shared.util.getText

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun TileSurah(
    modifier: Modifier = Modifier,
    surah: Surah,
    number: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = number, color = Color.Black, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = surah.name,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = surah.translation.getText(),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = surah.caligraphy,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(SharedFont.surah))
                )
            }
            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.2f))
        }
    }
}