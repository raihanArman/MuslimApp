package com.randev.dzikir.presentation.dzikir.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.raihan.ui.app_bar.AppBarCustom
import com.raihan.ui.tile.TileDzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.presentation.dzikir.viewmodel.DzikirViewModel
import com.randev.dzikir.util.toValues
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
fun NavGraphBuilder.dzikirNavigation() = kotlin.run {
    composable(Destination.DzikirScreen) { backStackEntry ->
        val navigator: AppNavigator = get<AppNavigator>()
        DzikirScreen(navigator = navigator, backStackEntry = backStackEntry)
    }
}

@Composable
fun DzikirScreen(navigator: AppNavigator, backStackEntry: NavBackStackEntry) {
    val category = backStackEntry.arguments?.getString("category") ?: ""
    val viewModel: DzikirViewModel = getViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val category = category.toValues()
        viewModel.load(request = DzikirRequest(category = category))
    }

    Scaffold(
        topBar = {
            AppBarCustom(title = "Dzikir $category", onBack = {
                navigator.tryNavigateBack()
            })
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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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
