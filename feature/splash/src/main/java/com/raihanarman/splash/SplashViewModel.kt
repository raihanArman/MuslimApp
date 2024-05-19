package com.raihanarman.splash

import androidx.lifecycle.viewModelScope
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.QuranRepository
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class SplashViewModel(
    private val repository: QuranRepository,
    private val navigator: AppNavigator
) : BaseViewModel() {

    private val _observeSetupQuran = MutableStateFlow<ResponseState<Unit>>(ResponseState.Loading())
    val observeSetupQuran = _observeSetupQuran.asStateFlow()

    private val _observeEvent = MutableSharedFlow<SplashEvent>()
    val observeEvent = _observeEvent.asSharedFlow()

    init {
        checkDataQuran()
    }

    private fun setupQuran() {
        viewModelScope.launch {
            repository.setupQuran().collect {
                _observeSetupQuran.value = it
                when (it) {
                    is ResponseState.Success -> {
                        onEvent(SplashEvent.OnNavigateToMain)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun checkDataQuran() {
        launch {
            if (repository.checkDataIsExists()) {
                onEvent(SplashEvent.OnNavigateToMain)
            } else {
                setupQuran()
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        launch {
            _observeEvent.emit(event)
        }
    }

    fun goToDashboard() {
        navigator.tryNavigateAndReplaceStartRoute(Destination.DashboardScreen())
    }
}
