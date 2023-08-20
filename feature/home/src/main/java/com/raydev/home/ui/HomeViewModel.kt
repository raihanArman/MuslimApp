package com.raydev.home.ui

import android.os.CountDownTimer
import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.base.BaseViewModel
import com.raihanarman.location.LocationManager
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.usecase.prayer.GetCurrentPrayerTimeUseCase
import com.raydev.domain.usecase.prayer.GetPrayerTimeUseCase
import com.raydev.home.utils.tick
import com.raydev.shared.model.NextPrayerTime
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class HomeViewModel(
    private val repository: PrayerRepository
): BaseViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    var newtimer: CountDownTimer? = null

    init {
        getPrayerTime()
        buildNextPrayerTime()
    }

    private fun getPrayerTime() {
        launch {
            repository.getCurrentPrayerTime().collect { prayerTime ->
                val nextPrayerTime = repository.getNextPrayerTime(prayerTime)
                _state.update {
                    it.copy(
                        prayerTime = prayerTime,
                        hijrDate = repository.getCurrentHijrDate()
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        launch {
            _event.emit(event)
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