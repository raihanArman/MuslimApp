package com.raihanarman.read_quran.ui

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import com.raihanarman.read_quran.model.PagerStateSnapshot
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.usecase.quran.GetAyahBySurahIdUseCase
import com.raydev.domain.usecase.quran.GetSurahAyahUseCase
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.navigation.Destination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class ReadQuranViewModel(
    private val ayahBySurah: GetAyahBySurahIdUseCase,
    private val surahUseCase: GetSurahUseCase,
    private val stateHandle: SavedStateHandle,
): BaseViewModel() {

    init {
        getSurah()
        val surahId = stateHandle.get<String>(Destination.ReadQuranScreen.SURAH_ID_KEY) ?: ""
        setTabSelected(surahId.toInt())
        getAyah(surahId.toInt())
    }

    private val _state: MutableStateFlow<ReadQuranState> = MutableStateFlow(ReadQuranState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<ReadQuranEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val ayahBySurahIdFlow = _state.filter {
        it.surahId != null
    }.flatMapLatest {
        ayahBySurah.invoke(it.surahId!!)
    }.distinctUntilChanged()

    private fun setTabSelected(id: Int) {
        launch {
            _state.update {
                it.copy(
                    tabsSelected = id
                )
            }
        }
    }

    fun getAyah(surahId: Int) {
        launch {
            ayahBySurah.invoke(surahId).collect {
                _state.update { state ->
                    state.copy(
                        listAyah = it,
                        surahId = surahId,
                    )
                }
            }
        }
    }

    private fun getSurah() {
        launch {
            surahUseCase.invoke().collect {
                _state.update { state ->
                    state.copy(listSurah = it)
                }
            }
        }
    }

    fun onEvent(event: ReadQuranEvent) {
        launch {
            _event.emit(event)
            when(event) {
                ReadQuranEvent.Initial -> {}
                is ReadQuranEvent.OnClickTabSurah -> {
                    _state.update { state ->
                        state.copy(
                            tabsSelected = event.id,
                            surahId = event.id
                        )
                    }
                    getAyah(event.id)
                }
            }
        }
    }

}