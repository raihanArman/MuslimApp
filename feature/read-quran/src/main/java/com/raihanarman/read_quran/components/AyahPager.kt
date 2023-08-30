package com.raihanarman.read_quran.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.raihanarman.read_quran.ui.ReadQuranEvent
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AyahPager(
    pagerState: PagerState,
    lazyListState: LazyListState,
    listSurah: List<Surah>,
    listAyah: List<Ayah>,
    surahSelected: Surah,
    onEvent: (ReadQuranEvent) -> Unit,
    onScrolling: () -> Unit
) {
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(ReadQuranEvent.OnClickTabSurah(page + 1))
        }
    }

    HorizontalPager(
        state = pagerState,
        pageCount = listSurah.size
    ) {
        LazyColumn(
            state = lazyListState,
        ) {
            itemsIndexed(
                items = listAyah,
                key = { _, item ->
                    item.id
                }
            ) { index, item ->
                if (item.useBismillah == true) {
                    BissmillahItem()
                }
                AyahItem(
                    ayah = item,
                    number = (index + 1).toString(),
                    modifier = Modifier.background(
                        if (index % 2 == 0) Color.White
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    onEvent(
                        ReadQuranEvent.OnClickAyah(
                            surah = surahSelected,
                            ayah = it,
                            ayahIndexSelected = index
                        )
                    )
                }
            }
        }

        onScrolling()
    }
}
