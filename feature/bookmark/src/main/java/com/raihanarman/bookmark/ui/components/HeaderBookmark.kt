package com.raihanarman.bookmark.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raihan.ui.app_bar.AppBarCustom

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
