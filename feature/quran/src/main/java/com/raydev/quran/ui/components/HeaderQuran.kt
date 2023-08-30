package com.raydev.quran.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raihan.ui.app_bar.AppBarCustom
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun HeaderQuran(
    modifier: Modifier = Modifier,
    onMenuClicked: (HeaderQuranMenu) -> Unit
) {
    AppBarCustom(
        modifier = modifier,
        title = "Al Quran",
        action = {
            IconButton(onClick = {
                onMenuClicked(HeaderQuranMenu.OnOpenFilterDialog)
            }) {
                Icon(
                    painter = painterResource(id = SharedDrawable.baseline_low_priority_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                onMenuClicked(HeaderQuranMenu.OnNavigateToBookmark)
            }) {
                Icon(
                    painter = painterResource(id = SharedDrawable.baseline_bookmarks_24),
                    contentDescription = null
                )
            }
        }
    )
}

sealed interface HeaderQuranMenu {
    object OnNavigateToBookmark : HeaderQuranMenu
    object OnOpenFilterDialog : HeaderQuranMenu
}
