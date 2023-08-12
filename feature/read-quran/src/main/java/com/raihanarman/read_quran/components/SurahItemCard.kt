package com.raihanarman.read_quran.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@Composable
fun SurahItemCard(
    modifier: Modifier = Modifier,
    surah: Surah,
    onClick: () -> Unit,
    selectedId: Int
) {
    Box(
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            text = surah.name,
            fontSize = 14.sp,
            color = if (surah.id == selectedId) Color.White else MaterialTheme.colorScheme.primary
        )
    }
}