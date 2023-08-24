package com.raihanarman.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.domain.usecase.quran.SetupQuranUseCase
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class SplashViewModel(
    private val setupQuranUseCase: SetupQuranUseCase,
    private val getSurahUseCase: GetSurahUseCase,
    private val navigator: AppNavigator
): BaseViewModel() {

    private val _observeSetupQuran = MutableStateFlow<ResponseState<Unit>>(ResponseState.Loading())
    val observeSetupQuran = _observeSetupQuran.asStateFlow()


    private val _observeEvent = MutableSharedFlow<SplashEvent>()
    val observeEvent = _observeEvent.asSharedFlow()

    init {
        setupQuran()
    }

    fun setupQuran() {
        viewModelScope.launch {
            setupQuranUseCase.invoke().collect {
                _observeSetupQuran.value = it
                when(it) {
                    is ResponseState.Success -> {
//                        getSurah()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getSurah() {
        viewModelScope.launch {
            getSurahUseCase.invoke().collect {
                println("Ampas Kuda -> getSurah | $it")
            }
        }
    }

    fun onEvent(event: SplashEvent){
        launch {
            _observeEvent.emit(event)
        }
    }

    fun goToDashboard() {
        navigator.tryNavigateAndReplaceStartRoute(Destination.DashboardScreen())
    }

}