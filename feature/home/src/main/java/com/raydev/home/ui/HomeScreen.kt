package com.raydev.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raihan.ui.PrayerSection
import com.raydev.home.ui.components.CardLastReadQuran
import com.raydev.home.ui.components.CardMainInformation
import com.raydev.home.ui.components.HomeMenuSection
import com.raydev.home.ui.components.TileMosque
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
fun NavGraphBuilder.homeMainNavigation() = run {
    composable("home") {
        val viewModel: HomeViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        HomeScreen(
            onEvent = viewModel::onEvent,
            state = state
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        state.prayerTime?.let { prayerTime ->
            state.nextPrayerTime?.let { nextPrayerTime ->
                CardMainInformation(
                    prayerTime = prayerTime,
                    nextPrayerTime = nextPrayerTime,
                    hijrDate = state.hijrDate.orEmpty(),
                    currentDate = state.currentdate.orEmpty()
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrayerSection(
                    prayerTime = prayerTime,
                    nextPrayerTime = nextPrayerTime
                )
                Spacer(modifier = Modifier.height(16.dp))
                HomeMenuSection(onClick = {
                    onEvent(it)
                })
                Spacer(modifier = Modifier.height(16.dp))
                state.lastRead?.let {
                    CardLastReadQuran(lastRead = it) {
                        onEvent(HomeEvent.OnNavigateToReadQuran(it))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
                state.mosqueData?.let {
                    it.forEach { data ->
                        key(data.title) {
                            TileMosque(mosque = data)
                        }
                    }
                }
            }
        }
    }
}
