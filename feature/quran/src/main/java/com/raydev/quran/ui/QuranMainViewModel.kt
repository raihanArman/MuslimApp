package com.raydev.quran.ui

import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
class QuranMainViewModel(
    private val useCase: GetSurahUseCase,
    private val navigator: AppNavigator
): BaseViewModel() {

    private val _uiState: MutableStateFlow<QuranMainState> = MutableStateFlow(QuranMainState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<QuranMainEvent> = MutableSharedFlow<QuranMainEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getSurah()
    }
    private fun getSurah() {
        launch {
            useCase.invoke().collect {
                _uiState.update { state ->
                    state.copy(listSurah = it)
                }
                println("Ampas kuda -> ViewModel | $_uiState")
            }
        }
    }

    fun onEvent(event: QuranMainEvent) {
        launch {
            _uiEvent.emit(event)
            when(event) {
                QuranMainEvent.Initial -> {}
                is QuranMainEvent.OnClickSurah -> {
                    navigator.tryNavigateTo(
                        Destination.ReadQuranScreen(
                            id = event.id
                        )
                    )
                }
            }
        }
    }

}