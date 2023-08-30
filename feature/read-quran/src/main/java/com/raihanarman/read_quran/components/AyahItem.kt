package com.raihanarman.read_quran.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.model.Ayah
import com.raydev.shared.util.SharedDrawable
import com.raydev.shared.util.SharedFont
import com.raydev.shared.util.getArabic
import com.raydev.shared.util.getText

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@Composable
fun AyahItem(
    modifier: Modifier = Modifier,
    ayah: Ayah,
    number: String,
    onClick: (Ayah) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = modifier
                .clickable {
                    onClick(ayah)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    Text(text = number, color = Color.Black, fontSize = 14.sp)
                    if (ayah.isBookmark) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Icon(
                            painter = painterResource(id = SharedDrawable.baseline_bookmarks_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(13.dp)
                        )
                    }
                    if (ayah.isLastRead) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Icon(
                            painter = painterResource(id = SharedDrawable.outline_attachment_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(13.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = ayah.text.getArabic().replace(
                            if (ayah.verseNumber == 1 && ayah.chapterId != 1) "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ"
                            else "",
                            ""
                        ),
                        fontSize = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        fontFamily = FontFamily(
                            Font(SharedFont.uthman)
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = ayah.text.getText(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
