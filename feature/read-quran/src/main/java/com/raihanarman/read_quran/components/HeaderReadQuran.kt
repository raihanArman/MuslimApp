package com.raihanarman.read_quran.components

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
fun HeaderReadQuran(
    modifier: Modifier = Modifier,
    onMenuClicked: (HeaderReadQuranMenu) -> Unit,
    onBack: () -> Unit
) {
    AppBarCustom(
        modifier = modifier,
        title = "Surah",
        action = {
            IconButton(onClick = {
                onMenuClicked(HeaderReadQuranMenu.OnOpenFilterDialog)
            }) {
                Icon(
                    painter = painterResource(id = SharedDrawable.baseline_low_priority_24),
                    contentDescription = null
                )
            }
        },
        onBack = onBack
    )
}

sealed interface HeaderReadQuranMenu {
    object OnOpenFilterDialog : HeaderReadQuranMenu
}
