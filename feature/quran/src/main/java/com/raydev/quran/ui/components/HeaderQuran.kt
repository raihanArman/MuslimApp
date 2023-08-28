package com.raydev.quran.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.raihan.ui.app_bar.AppBarCustom
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun HeaderQuran(
    modifier: Modifier = Modifier,
    onNavigateToBookmark: () -> Unit
) {
    AppBarCustom(
        modifier = modifier,
        title = "Al Quran",
        action = {
            IconButton(onClick = onNavigateToBookmark) {
                Icon(
                    painter = painterResource(id = SharedDrawable.baseline_bookmarks_24),
                    contentDescription = null
                )
            }
        }
    )
}
