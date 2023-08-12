package com.raydev.quran.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raydev.quran.components.SurahItem
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
fun NavGraphBuilder.quranMainNavigation() = run {
    composable("quran_main"){
        val viewModel: QuranMainViewModel = getViewModel()
        val event by viewModel.uiEvent.collectAsState(QuranMainEvent.Initial)
        val state by viewModel.uiState.collectAsState()
        SurahScreen(
            onEvent = viewModel::onEvent,
            event = event,
            state =  state
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurahScreen(
    onEvent: (QuranMainEvent) -> Unit,
    state: QuranMainState,
    event: QuranMainEvent
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        state.listSurah?.let {
            println("Ampas kuda -> SurahScreen | $it")
            LazyColumn {
                itemsIndexed(
                    items = it
                ) { index, item ->
                    SurahItem(
                        surah = item,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing,
                            )
                        )
                    ) {
                        onEvent(QuranMainEvent.OnClickSurah(index))
                    }
                }
            }
        }
    }
}