package com.raydev.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
fun NavGraphBuilder.homeMainNavigation() = run {
    composable("home"){
        val viewModel: HomeViewModel = getViewModel()
        val event by viewModel.event.collectAsState(HomeEvent.Initial)
        val state by viewModel.state.collectAsState()
        HomeScreen(
            onEvent = viewModel::onEvent,
            event = event,
            state =  state
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    event: HomeEvent,
    onEvent: (HomeEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.prayerTime?.let {
            Text(
                text = it.toString(),
                color = Color.Black,
                fontSize = 30.sp
            )
        }
    }
}