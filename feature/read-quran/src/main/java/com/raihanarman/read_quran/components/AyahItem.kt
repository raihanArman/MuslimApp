package com.raihanarman.read_quran.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.database.entity.LanguageString
import com.raydev.shared.model.Ayah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
fun ArrayList<LanguageString>.getText():String{
    return filter { it.language == "bahasa" }.firstOrNull()?.text ?: ""
}
fun ArrayList<LanguageString>.getArabic():String{
    return filter { it.language == "arabic" }.firstOrNull()?.text.toString().replace("،","")
}
@Composable
fun AyahItem(
    modifier: Modifier = Modifier,
    ayah: Ayah,
    number: String,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
            ) {
                Text(text = number, color = Color.Black)
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = ayah.text.getArabic().replace(
                            if (ayah.verseNumber == 1 && ayah.chapterId != 1) "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ" else "",
                            ""
                        ),
                        fontSize = 22.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = ayah.text.getText(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}