package com.raydev.prayer.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.raihan.ui.app_bar.AppBarCustom
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 12/09/23
 */
@Composable
fun HeaderPrayer(
    modifier: Modifier = Modifier,
    onMenuClicked: (HeaderPrayerMenu) -> Unit
) {
    AppBarCustom(
        modifier = modifier,
        title = "Sholat",
        action = {
            IconButton(onClick = {
                onMenuClicked(HeaderPrayerMenu.OnOpenQiblahDialog)
            }) {
                Icon(
                    painter = painterResource(id = SharedDrawable.baseline_location_searching_24),
                    contentDescription = null
                )
            }
        }
    )
}

sealed interface HeaderPrayerMenu {
    object OnOpenQiblahDialog : HeaderPrayerMenu
}
