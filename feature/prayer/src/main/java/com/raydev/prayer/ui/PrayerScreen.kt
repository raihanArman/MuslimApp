package com.raydev.prayer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raihan.ui.PrayerSection
import com.raihan.ui.card.CardNextPrayer
import com.raydev.prayer.R
import com.raydev.prayer.ui.components.PrayerRingSection
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
fun NavGraphBuilder.prayerMainNavigation() = run {
    composable("prayer"){
        val viewModel: PrayerViewModel = getViewModel()
        val event by viewModel.event.collectAsState(PrayetEvent.Initial)
        val state by viewModel.state.collectAsState()
        PrayerScreen(
            onEvent = viewModel::onEvent,
            event = event,
            state =  state
        )
    }
}

@Composable
fun PrayerScreen(
    state: PrayerState,
    event: PrayetEvent,
    onEvent: (PrayetEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        state.prayerTime?.let { prayerTime ->
            state.nextPrayerTime?.let { nextPrayerTime ->
                CardNextPrayer(
                    nextPrayerTime = nextPrayerTime,
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrayerSection(
                    prayerTime = prayerTime,
                    nextPrayerTime = nextPrayerTime
                )
                Spacer(modifier = Modifier.height(16.dp))
                PrayerRingSection(
                    prayerTime = prayerTime,
                    subuhIcon = {
                        PrayerIconRing(isRing = state.subuhRing) {

                        }
                    },
                    dhuhurIcon = {
                        PrayerIconRing(isRing = state.dhuhurRing) {

                        }
                    },
                    asharIcon = {
                        PrayerIconRing(isRing = state.asharRing) {

                        }
                    },
                    maghribIcon = {
                        PrayerIconRing(isRing = state.maghribRing) {

                        }
                    },
                    isyaIcon = {
                        PrayerIconRing(isRing = state.isyaRing) {

                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PrayerIconRing(isRing: Boolean, onClick: () -> Unit) {
    Icon(
        modifier = Modifier.clickable {
            onClick()
        },
        painter = painterResource(id = if (isRing) R.drawable.baseline_volume_up_24 else R.drawable.baseline_volume_mute_24),
        contentDescription = null
    )
}