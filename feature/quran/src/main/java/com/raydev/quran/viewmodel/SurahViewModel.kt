package com.raydev.quran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.QuranUseCase
import com.raydev.shared.model.Surah
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class SurahViewModel(
    private val quranUseCase: QuranUseCase
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableSurah: LiveData<ResponseState<List<Surah>>>
        get() = _observableSurah
    private val _observableSurah = MutableLiveData<ResponseState<List<Surah>>>()

    fun loadSurah() {
        uiScope.launch {
            val contentSurah = quranUseCase.getSurah()
            contentSurah.collect {
                _observableSurah.value = it
            }
        }
    }

}