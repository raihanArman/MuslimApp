package com.raihanarman.bookmark.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.BookmarkQuran

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun TileBookmark(
    modifier: Modifier = Modifier,
    bookmarkQuran: BookmarkQuran,
    onClick: (BookmarkQuran) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(bookmarkQuran)
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "QS. ${bookmarkQuran.surah?.name}",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Ayat ${bookmarkQuran.ayah?.verseNumber}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
