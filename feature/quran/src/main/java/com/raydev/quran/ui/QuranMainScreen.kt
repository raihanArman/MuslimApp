package com.raydev.quran.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raihan.ui.dialog.DialogAyahJump
import com.raydev.quran.ui.components.HeaderQuran
import com.raydev.quran.ui.components.HeaderQuranMenu
import com.raydev.quran.ui.components.TileSurah
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
fun NavGraphBuilder.quranMainNavigation() = run {
    composable("quran_main") {
        val viewModel: QuranMainViewModel = getViewModel()
        val state by viewModel.uiState.collectAsState()
        SurahScreen(
            onEvent = viewModel::onEvent,
            state = state
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurahScreen(
    onEvent: (QuranMainEvent) -> Unit,
    state: QuranMainState
) {
    Scaffold(
        topBar = {
            HeaderQuran {
                when (it) {
                    HeaderQuranMenu.OnNavigateToBookmark -> {
                        onEvent(QuranMainEvent.OnNavigateToBookmark)
                    }
                    HeaderQuranMenu.OnOpenFilterDialog -> {
                        onEvent(QuranMainEvent.OnOpenFilterDialog(true))
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            state.listSurah?.let {
                LazyColumn {
                    itemsIndexed(
                        items = it,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        TileSurah(
                            surah = item,
                            modifier = Modifier.animateItemPlacement(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing,
                                )
                            ),
                            number = "${index + 1}"
                        ) {
                            onEvent(QuranMainEvent.OnClickSurah(index))
                        }
                    }
                }
            }
        }
    }

    state.listSurah?.let {
        if (state.isOpenJumpDialog) {
            DialogAyahJump(
                listSurah = it,
                onDismissDialog = {
                    onEvent(QuranMainEvent.OnOpenFilterDialog(false))
                },
                onPositiveClick = { surah, ayat ->
                    onEvent(QuranMainEvent.OnNavigateToReadQuran(surah.id - 1, ayat))
                }
            )
        }
    }
}
