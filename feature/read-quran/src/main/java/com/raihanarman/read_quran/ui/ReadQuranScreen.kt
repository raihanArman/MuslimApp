package com.raihanarman.read_quran.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.raihanarman.read_quran.components.AyahPager
import com.raihanarman.read_quran.components.SurahTabLayout
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
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