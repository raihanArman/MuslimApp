package com.raihanarman.read_quran.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.raydev.shared.model.Surah
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurahTabLayout(
    pagerState: PagerState,
    listSurah: List<Surah>,
    pageSelected: Int,
    onClick: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        pagerState.scrollToPage(pageSelected)
    }

    ScrollableTabRow(
        modifier = Modifier.wrapContentHeight(),
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.White,
        contentColor = Color.White
    ) {
        listSurah.forEachIndexed { index, surah ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        onClick(surah.id)
                        pagerState.animateScrollToPage(index)
                    }
                },
            ) {
                Text(text = surah.name, color = Color.Black)
            }
        }
    }
}
