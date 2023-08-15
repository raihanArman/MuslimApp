package com.raydev.home.ui

import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.base.BaseViewModel
import com.raihanarman.location.LocationManager
import com.raydev.domain.usecase.prayer.GetPrayerTimeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class HomeViewModel(
    private val useCase: GetPrayerTimeUseCase
): BaseViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        getLocation()
    }

    private fun getLocation() {
        launch {
            LocationManager.instance.apply {
                getLocationFlowEvent().collect { location ->
                    val prayerTime = useCase.invoke(LatLng(location.latitude, location.longitude))
                    _state.update {
                        it.copy(
                            location = prayerTime.address
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        launch {
            _event.emit(event)
        }
    }

}