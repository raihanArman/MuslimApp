package com.raihanarman.read_quran.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.ui.bottom_sheet.BaseBottomSheet
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun QuranBottomSheet(
    modifier: Modifier = Modifier,
    surah: Surah,
    ayah: Ayah,
    onClick: (QuranBottomSheetMenu) -> Unit,
    onDismiss: () -> Unit
) {
    BaseBottomSheet(onDismiss = onDismiss) {
        Box(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "QS. ${surah.name}: Ayat ${ayah.verseNumber}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                QuranRowMenu(icon = SharedDrawable.baseline_bookmarks_24, text = "Tambahkan ke Bookmark") {
                    onClick(QuranBottomSheetMenu.OnBookmark(surah, ayah))
                }
                QuranRowMenu(icon = SharedDrawable.outline_attachment_24, text = "Tandai ke Bacaan Terakhir") {
                    onClick(QuranBottomSheetMenu.OnLastRead(surah, ayah))
                }
                QuranRowMenu(icon = SharedDrawable.baseline_content_copy_24, text = "Salin") {
                    onClick(QuranBottomSheetMenu.OnCopy(surah, ayah))
                }
                QuranRowMenu(icon = SharedDrawable.baseline_share_24, text = "Share") {
                    onClick(QuranBottomSheetMenu.OnShare(surah, ayah))
                }
            }
        }
    }
}

@Composable
fun QuranRowMenu(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .background(Color.White)
    ) {
        Row {
            Icon(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, fontSize = 16.sp, color = Color.Black)
        }
    }
}

sealed interface QuranBottomSheetMenu {
    data class OnBookmark(val surah: Surah, val ayah: Ayah): QuranBottomSheetMenu
    data class OnLastRead(val surah: Surah, val ayah: Ayah): QuranBottomSheetMenu
    data class OnCopy(val surah: Surah, val ayah: Ayah): QuranBottomSheetMenu
    data class OnShare(val surah: Surah, val ayah: Ayah): QuranBottomSheetMenu
}