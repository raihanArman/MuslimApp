package com.raydev.prayer.ui

import android.os.CountDownTimer
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.repository.PrayerRepository
import com.raydev.prayer.work.ReminderHelper
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.RingType
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
    private val repository: PrayerRepository,
    private val reminderHelper: ReminderHelper,
) : BaseViewModel() {
    private val _state: MutableStateFlow<PrayerState> = MutableStateFlow(PrayerState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<PrayetEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private var newtimer: CountDownTimer? = null

    init {
        getPrayerTime()
        getPrayerRing()
        buildNextPrayerTime()
    }

    private fun getPrayerRing() {
        launch {
            _state.update {
                it.copy(
                    subuhRing = repository.getSubuhData().ringType == RingType.SOUND,
                    dhuhurRing = repository.getDhuhurData().ringType == RingType.SOUND,
                    asharRing = repository.getAsharData().ringType == RingType.SOUND,
                    maghribRing = repository.getMaghribData().ringType == RingType.SOUND,
                    isyaRing = repository.getIsyaData().ringType == RingType.SOUND,
                )
            }
        }
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
        newtimer = tick(millisInFuture = Long.MAX_VALUE, interval = 1000L, onTick = {
            val prayerTime = repository.getPrayerTime()
            _state.update {
                it.copy(
                    nextPrayerTime = repository.getNextPrayerTime(prayerTime)
                )
            }
        })
        newtimer?.start()
    }
    fun onEvent(event: PrayetEvent) {
        launch {
            when (event) {
                PrayetEvent.Initial -> {}
                is PrayetEvent.SetRingingAshar -> {
                    setRingingAshar(event.value)
                }
                is PrayetEvent.SetRingingDhuhur -> {
                    setRingingDhuhur(event.value)
                }
                is PrayetEvent.SetRingingIsya -> {
                    setRingingIsya(event.value)
                }
                is PrayetEvent.SetRingingMaghrib -> {
                    setRingingMaghrib(event.value)
                }
                is PrayetEvent.SetRingingSubuh -> {
                    setRingingSubuh(event.value)
                }
            }
            _event.emit(event)
        }
    }

    private fun setRingingSubuh(value: Int) {
        launch {
            val newValue = value == RingType.SOUND

            repository.setSubuhData(prayerData = PrayerData(ringType = value))
            reminderHelper.setAlarmSubuh(
                prayerTime = repository.getPrayerTime(),
                isRing = newValue
            )
            _state.update {
                it.copy(
                    subuhRing = newValue
                )
            }
        }
    }

    private fun setRingingDhuhur(value: Int) {
        launch {
            val newValue = value == RingType.SOUND

            repository.setDhuhurData(prayerData = PrayerData(ringType = value))
            reminderHelper.setAlarmDhuhur(
                prayerTime = repository.getPrayerTime(),
                isRing = newValue
            )
            _state.update {
                it.copy(
                    dhuhurRing = newValue
                )
            }
        }
    }

    private fun setRingingAshar(value: Int) {
        launch {
            val newValue = value == RingType.SOUND

            repository.setAsharData(prayerData = PrayerData(ringType = value))
            reminderHelper.setAlarmAsr(
                prayerTime = repository.getPrayerTime(),
                isRing = newValue
            )
            _state.update {
                it.copy(
                    asharRing = newValue
                )
            }
        }
    }

    private fun setRingingMaghrib(value: Int) {
        launch {
            val newValue = value == RingType.SOUND

            repository.setMaghribData(prayerData = PrayerData(ringType = value))
            reminderHelper.setAlarmMaghrib(
                prayerTime = repository.getPrayerTime(),
                isRing = newValue
            )
            _state.update {
                it.copy(
                    maghribRing = newValue
                )
            }
        }
    }

    private fun setRingingIsya(value: Int) {
        launch {
            val newValue = value == RingType.SOUND

            repository.setIsyaData(prayerData = PrayerData(ringType = value))
            reminderHelper.setAlarmIsya(
                prayerTime = repository.getPrayerTime(),
                isRing = newValue
            )
            _state.update {
                it.copy(
                    isyaRing = newValue
                )
            }
        }
    }
}
