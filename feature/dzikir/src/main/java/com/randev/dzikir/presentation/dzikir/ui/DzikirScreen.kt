package com.randev.dzikir.presentation.dzikir.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raihan.ui.app_bar.AppBarCustom
import com.raihan.ui.tile.TileDzikir
import com.randev.dzikir.presentation.dzikir.viewmodel.DzikirViewModel
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
@Composable
fun DzikirScreen(modifier: Modifier = Modifier) {
    val viewModel: DzikirViewModel = getViewModel()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBarCustom(title = "Dzikir Pagi", onBack = {})
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    state.data?.let { data ->
                        items(
                            items = data,
                            key = { item ->
                                item.id
                            }
                        ) {
                            TileDzikir(
                                content = it.content,
                                title = it.title,
                                translate = it.translate,
                                times = it.times
                            )
                        }
                    }
                }
            }
        }
    )
}
