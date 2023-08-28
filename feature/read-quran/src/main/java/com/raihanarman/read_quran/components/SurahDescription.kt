package com.raihanarman.read_quran.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah
import com.raydev.shared.util.getText

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@Composable
fun SurahDescription(
    modifier: Modifier = Modifier,
    surah: Surah,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = surah.name, color = Color.Black, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = surah.translation.getText(), color = Color.Gray, fontSize = 14.sp)
        }
    }
}
