package com.raydev.prayer.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.raihan.ui.PrayerSection
import com.raihan.ui.card.CardNextPrayer
import com.raihanarman.qiblah.QiblaScreen
import com.raydev.prayer.ui.components.HeaderPrayer
import com.raydev.prayer.ui.components.PrayerIconRing
import com.raydev.prayer.ui.components.PrayerRingSection
import com.raydev.shared.model.RingType
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
fun NavGraphBuilder.prayerMainNavigation() = run {
    composable(
        "prayer",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://muslimapp.com/prayer"
                action = Intent.ACTION_VIEW
            }
        )
    ) {

        val viewModel: PrayerViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        PrayerScreen(
            onEvent = viewModel::onEvent,
            state = state
        )
    }
}

@Composable
fun PrayerScreen(
    state: PrayerState,
    onEvent: (PrayetEvent) -> Unit
) {
    Scaffold(
        topBar = {
            HeaderPrayer {
                onEvent(PrayetEvent.OnOpenQiblahDialog(true))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp)
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
                                onEvent(
                                    PrayetEvent.SetRingingSubuh(
                                        if (state.subuhRing) RingType.SILENT
                                        else RingType.SOUND
                                    )
                                )
                            }
                        },
                        dhuhurIcon = {
                            PrayerIconRing(isRing = state.dhuhurRing) {
                                onEvent(
                                    PrayetEvent.SetRingingDhuhur(
                                        if (state.dhuhurRing) RingType.SILENT
                                        else RingType.SOUND
                                    )
                                )
                            }
                        },
                        asharIcon = {
                            PrayerIconRing(isRing = state.asharRing) {
                                onEvent(
                                    PrayetEvent.SetRingingAshar(
                                        if (state.asharRing) RingType.SILENT
                                        else RingType.SOUND
                                    )
                                )
                            }
                        },
                        maghribIcon = {
                            PrayerIconRing(isRing = state.maghribRing) {
                                onEvent(
                                    PrayetEvent.SetRingingMaghrib(
                                        if (state.maghribRing) RingType.SILENT
                                        else RingType.SOUND
                                    )
                                )
                            }
                        },
                        isyaIcon = {
                            PrayerIconRing(isRing = state.isyaRing) {
                                onEvent(
                                    PrayetEvent.SetRingingIsya(
                                        if (state.isyaRing) RingType.SILENT
                                        else RingType.SOUND
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    if (state.isOpenQiblahDialog) {
        QiblaScreen(
            modifier = Modifier.height(400.dp),
            currentLatitude = state.userLatitude
        ) {
            onEvent(PrayetEvent.OnOpenQiblahDialog(false))
        }
    }
}
