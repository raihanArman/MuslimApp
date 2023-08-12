package com.raydev.home.ui

import com.raydev.anabstract.base.BaseViewModel
import com.raydev.shared.LocationManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class HomeViewModel: BaseViewModel() {

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
                getLastLocation {
                    getCityLocation(it) { address ->
                        println("AMPPPPPP -> $address")
                        address?.let {
                            _state.update {
                                it.copy(
                                    location = address.subAdminArea
                                )
                            }
                        }
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