package com.raihanarman.read_quran.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah
import com.raydev.shared.util.getText

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@Composable
fun SurahDetailItem(
    modifier: Modifier = Modifier,
    surah: Surah
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${surah.name}(${surah.translation.getText()})",
                    fontSize = 11.sp,
                    color = Color.Black
                )
                Text(
                    text = "${surah.revelation} ${surah.verses} Ayat",
                    fontSize = 11.sp,
                    color = Color.Black
                )
            }
        }
    }
}
