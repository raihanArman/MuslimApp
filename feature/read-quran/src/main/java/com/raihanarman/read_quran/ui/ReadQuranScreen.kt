package com.raihanarman.read_quran.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.raihan.ui.dialog.DialogAyahJump
import com.raihanarman.read_quran.components.AyahPager
import com.raihanarman.read_quran.components.HeaderReadQuran
import com.raihanarman.read_quran.components.QuranBottomSheet
import com.raihanarman.read_quran.components.QuranBottomSheetMenu
import com.raihanarman.read_quran.components.SurahTabLayout
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun NavGraphBuilder.readQuranNavigation() = run {
    composable(Destination.ReadQuranScreen) {
        val viewModel: ReadQuranViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        ReadQuranScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadQuranScreen(
    state: ReadQuranState,
    onEvent: (ReadQuranEvent) -> Unit
) {
    val pagerState = rememberPagerState()
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isDataShow by remember { mutableStateOf(false) }

    val doneScrollingTab by remember {
        derivedStateOf {
            !pagerState.isScrollInProgress
        }
    }

    LaunchedEffect(key1 = state.indexBookmark) {
        if (state.indexBookmark != null && isDataShow) {
            delay(1000)
            scrollState.animateScrollToItem(state.indexBookmark + 1)
        }
    }

    Scaffold(
        topBar = {
            HeaderReadQuran(
                onMenuClicked = {
                    onEvent(ReadQuranEvent.OnOpenFilterDialog(true))
                },
                onBack = {
                    onEvent(ReadQuranEvent.OnNavigateBack)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                state.listSurah?.let { surah ->
                    state.tabsSelected?.let { surahSelected ->
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Box(modifier = Modifier.padding(vertical = 10.dp)) {
                                SurahTabLayout(
                                    pagerState = pagerState,
                                    listSurah = surah,
                                    pageSelected = surahSelected,
                                    onClick = {
                                        onEvent(ReadQuranEvent.OnClickTabSurah(it))
                                    },
                                )
                            }
                            state.listAyah?.let {
                                AyahPager(
                                    pagerState = pagerState,
                                    lazyListState = scrollState,
                                    listSurah = surah,
                                    listAyah = it,
                                    surahSelected = surah[pagerState.currentPage],
                                    onEvent = onEvent,
                                    onDataShow = {
                                        isDataShow = true
                                        onEvent(ReadQuranEvent.OnScrollToBookmark)
                                    },
                                    isDoneScrollingTab = doneScrollingTab
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    if (state.bottomSheetIsOpen) {
        if (state.surahSelected != null && state.ayahSelected != null) {
            QuranBottomSheet(
                surah = state.surahSelected,
                ayah = state.ayahSelected,
                onClick = {
                    when (it) {
                        is QuranBottomSheetMenu.OnBookmark -> {
                            onEvent(ReadQuranEvent.OnBookmarkAyah)
                        }
                        is QuranBottomSheetMenu.OnCopy -> {}
                        is QuranBottomSheetMenu.OnLastRead -> {
                            onEvent(ReadQuranEvent.OnLastReadAyah)
                        }
                        is QuranBottomSheetMenu.OnShare -> {}
                    }
                },
                onDismiss = {
                    onEvent(ReadQuranEvent.OnCloseBottomSheet)
                }
            )
        }
    }

    if (state.isOpenJumpDialog && state.tabsSelected != null && state.listSurah != null) {
        DialogAyahJump(
            surah = state.listSurah[state.tabsSelected],
            onDismissDialog = {
                onEvent(ReadQuranEvent.OnOpenFilterDialog(false))
            },
            onPositiveClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(it)
                }
                onEvent(ReadQuranEvent.OnOpenFilterDialog(false))
            }
        )
    }
}
