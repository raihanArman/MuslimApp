package com.raydev.quran.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
@Composable
fun SurahItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                "${surah.name}",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                fontSize = 16.sp
            )
        }
        Divider(
            color = Color.LightGray
        )
    }
}

@Preview
@Composable
fun PreviewSurahItem() {
//    SurahItem(
//        modifier = Modifier,
//        surah = Surah(
//            1,
//            "",
//            0,
//            "Al-fatihah",
//            1
//        ),
//        onClick = {}
//    )
}