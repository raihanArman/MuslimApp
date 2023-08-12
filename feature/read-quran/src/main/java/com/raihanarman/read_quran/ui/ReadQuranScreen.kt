package com.raihanarman.read_quran.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.raihanarman.read_quran.components.AyahItem
import com.raihanarman.read_quran.components.AyahPager
import com.raihanarman.read_quran.components.SurahItemCard
import com.raihanarman.read_quran.components.SurahTabLayout
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import com.raydev.shared.database.entity.LanguageString
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun NavGraphBuilder.readQuranNavigation() = run {
    composable(Destination.ReadQuranScreen){
        val viewModel: ReadQuranViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        val event by viewModel.event.collectAsState(initial = ReadQuranEvent.Initial)
        ReadQuranScreen(
            state = state,
            event = event,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadQuranScreen(
    state: ReadQuranState,
    event: ReadQuranEvent,
    onEvent: (ReadQuranEvent) -> Unit
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.listSurah?.let { surah ->
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Box(modifier = Modifier.padding(vertical = 10.dp)) {
                    state.tabsSelected?.let {
                        println("Ampas Kuda -> ananaananan Surah id = $it")
                        SurahTabLayout(pagerState = pagerState, listSurah = surah, pageSelected = state.tabsSelected){
                            onEvent(ReadQuranEvent.OnClickTabSurah(it))
                        }
                    }
                }
                state.listAyah?.let {
                    AyahPager(
                        pagerState = pagerState,
                        listSurah = surah,
                        listAyah = it,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}