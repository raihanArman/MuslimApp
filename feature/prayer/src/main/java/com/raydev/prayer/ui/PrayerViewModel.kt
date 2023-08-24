package com.raydev.prayer.ui

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.util.getCurrentDate
import com.raydev.shared.util.tick
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
class PrayerViewModel(
    private val repository: PrayerRepository
): BaseViewModel() {
    private val _state: MutableStateFlow<PrayerState> = MutableStateFlow(PrayerState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<PrayetEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private var newtimer: CountDownTimer? = null

    init {
        getPrayerTime()
        buildNextPrayerTime()
    }

    private fun getPrayerTime() {
        launch {
            repository.getCurrentPrayerTime().collect { prayerTime ->
                _state.update {
                    it.copy(
                        prayerTime = prayerTime,
                    )
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
    fun onEvent(event: PrayetEvent) {
        launch {
            _event.emit(event)
        }
    }

}