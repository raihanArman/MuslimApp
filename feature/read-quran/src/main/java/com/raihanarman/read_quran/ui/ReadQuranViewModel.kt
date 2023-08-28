package com.raihanarman.read_quran.ui

import androidx.lifecycle.SavedStateHandle
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.repository.LastReadRepository
import com.raydev.domain.usecase.quran.BookmarkAyahUseCase
import com.raydev.domain.usecase.quran.GetAyahBySurahIdUseCase
import com.raydev.domain.usecase.quran.GetSurahUseCase
import com.raydev.navigation.Destination
import com.raydev.shared.model.BookmarkQuran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
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
    private val bookmarkAyahUseCase: BookmarkAyahUseCase,
    private val lastReadRepository: LastReadRepository,
) : BaseViewModel() {
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

    private fun getAyah(surahId: Int) {
        launch(Dispatchers.IO) {
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

    private fun goToIndexBookmarkFromAyah() {
        launch {
            val ayahVerseNumber = stateHandle.get<String>(Destination.ReadQuranScreen.VERSE_NUMBER)
            val indexBookmark = _state.value.listAyah?.indexOfFirst {
                it.verseNumber == ayahVerseNumber?.toInt()
            }

            if (indexBookmark != null && indexBookmark != -1) {
                _state.update {
                    it.copy(
                        indexBookmark = indexBookmark
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
            when (event) {
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

                is ReadQuranEvent.OnClickAyah -> {
                    _state.update { state ->
                        state.copy(
                            ayahSelected = event.ayah,
                            surahSelected = event.surah,
                            bottomSheetIsOpen = true,
                            ayahIndexSelected = event.ayahIndexSelected
                        )
                    }
                }

                ReadQuranEvent.OnCloseBottomSheet -> {
                    _state.update { state ->
                        state.copy(
                            bottomSheetIsOpen = false,
                        )
                    }
                }

                ReadQuranEvent.OnBookmarkAyah -> {
                    onBookmarkAyah()
                }

                ReadQuranEvent.OnScrollToBookmark -> {
                    goToIndexBookmarkFromAyah()
                }

                ReadQuranEvent.OnLastReadAyah -> {
                    onLastReadAyah()
                }
            }
        }
    }

    private fun onLastReadAyah() {
        launch(Dispatchers.IO) {
            val surah = _state.value.surahSelected
            val ayah = _state.value.ayahSelected
            val sumAyah = _state.value.listAyah?.size
            if (surah != null && ayah != null && sumAyah != null) {
                val newAyahState = ayah.copy(
                    isLastRead = true
                )

                val oldAyahState = _state.value.listAyah?.find {
                    it.isLastRead
                }

                lastReadRepository.setLastRead(
                    ayah = ayah,
                    surah = surah,
                    sumAyah = sumAyah
                )
                _state.update { state ->
                    state.copy(
                        bottomSheetIsOpen = false,
                        listAyah = state.listAyah?.toMutableList()?.apply {
                            set(state.ayahIndexSelected, newAyahState)
                            oldAyahState?.let {
                                val index = state.listAyah.indexOf(it)
                                set(index, it.copy(isLastRead = false))
                            }
                        }?.toList()
                    )
                }
            }
        }
    }

    private fun onBookmarkAyah(coba: String = "") {
        launch(Dispatchers.IO) {
            val surah = _state.value.surahSelected
            val ayah = _state.value.ayahSelected
            if (surah != null && ayah != null) {
                bookmarkAyahUseCase.invoke(
                    BookmarkQuran(
                        id = null,
                        surah, ayah
                    )
                ).collect { isBookmark ->
                    val newAyahState = ayah.copy(
                        isBookmark = isBookmark
                    )

                    _state.update { state ->
                        state.copy(
                            bottomSheetIsOpen = false,
                            listAyah = state.listAyah?.toMutableList()?.apply {
                                set(state.ayahIndexSelected, newAyahState)
                            }?.toList()
                        )
                    }
                }
            }
        }
    }
}
