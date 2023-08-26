package com.raihanarman.bookmark.ui.components

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
fun HeaderBookmark(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    AppBarCustom(
        modifier = modifier,
        title = "Bookmark",
        onBack = onBack
    )
}