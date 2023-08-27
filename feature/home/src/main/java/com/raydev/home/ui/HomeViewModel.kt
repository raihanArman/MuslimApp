package com.raydev.home.ui

import android.os.CountDownTimer
import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.base.BaseViewModel
import com.raihanarman.location.LocationManager
import com.raydev.domain.repository.LastReadRepository
import com.raydev.domain.repository.PrayerRepository
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.util.getCurrentDate
import com.raydev.shared.util.tick
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class HomeViewModel(
    private val repository: PrayerRepository,
    private val lastReadRepository: LastReadRepository,
    private val appNavigator: AppNavigator
): BaseViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private var newtimer: CountDownTimer? = null

    init {
        getPrayerTime()
        buildNextPrayerTime()
        getLastRead()
    }

    private fun getLastRead() {
        launch {
            lastReadRepository.getLastRead().collect { lastRead ->
                _state.update {
                    it.copy(
                        lastRead = lastRead
                    )
                }
            }
        }
    }

    private fun getPrayerTime() {
        launch {
            repository.getCurrentPrayerTime().collect { prayerTime ->
                _state.update {
                    it.copy(
                        prayerTime = prayerTime,
                        hijrDate = repository.getCurrentHijrDate(),
                        currentdate = getCurrentDate()
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        launch {
            _event.emit(event)
            when(event) {
                HomeEvent.Initial -> {}
                is HomeEvent.OnNavigateToReadQuran -> {
                    appNavigator.tryNavigateTo(
                        Destination.ReadQuranScreen(
                        event.lastRead.surahId - 1,
                        event.lastRead.ayah
                    ))
                }
            }
        }
    }

    private fun buildNextPrayerTime() {
        newtimer?.cancel()
        newtimer = tick(Long.MAX_VALUE, 1000) {
            val prayerTime = repository.getPrayerTime()
            _state.update {
                it.copy(
                    nextPrayerTime = repository.getNextPrayerTime(prayerTime)
                )
            }
        }
        newtimer?.start()
    }
}