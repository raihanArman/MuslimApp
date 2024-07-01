package com.raihan.ui.pager

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @author Raihan Arman
 * @date 30/06/24
 */
@Composable
fun VerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<String>,
    initialPage: Int? = 0,
    showUploadDate: Boolean = false,
    onClickComment: (videoId: String) -> Unit
) {
}
