package com.raydev.dailyduas.presentation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.navigation.NavGraphBuilder
import com.raihan.ui.app_bar.AppBarCustom
import com.raihan.ui.helper.OnceLaunchedEffect
import com.raihan.ui.tile.TileDailyDuas
import com.raydev.dailyduas.presentation.viewmodel.DailyDuasViewModel
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 22/05/24
 */

fun NavGraphBuilder.dailyDuasNavigation() = kotlin.run {
    composable(Destination.DailyDuasScreen) {
        val navigator: AppNavigator = get<AppNavigator>()
        DailyDuasScreen(
            navigator = navigator
        )
    }
}

@Composable
fun DailyDuasScreen(navigator: AppNavigator) {
    val viewModel: DailyDuasViewModel = getViewModel()
    val state by viewModel.uiState.collectAsState()

    OnceLaunchedEffect(
        viewModel = viewModel,
        effect = {
            viewModel.load()
        },
        content = {
            Scaffold(
                topBar = {
                    AppBarCustom(
                        title = "Doa-doa harian",
                        onBack = {
                            navigator.tryNavigateBack()
                        }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            state.data?.let { dailyDuasList ->
                                items(
                                    items = dailyDuasList,
                                    key = { item ->
                                        item.id
                                    }
                                ) { dailyDuas ->
                                    TileDailyDuas(
                                        title = dailyDuas.title,
                                        content = dailyDuas.content
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    )
}
