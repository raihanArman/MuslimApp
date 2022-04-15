package com.raydev.quran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.QuranUseCase
import com.raydev.shared.model.Ayat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AyatViewModel(
    private val useCase: QuranUseCase
): ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableAyat: LiveData<ResponseState<List<Ayat>>>
        get() = _observableAyat
    private val _observableAyat = MutableLiveData<ResponseState<List<Ayat>>>()

    fun loadAyat(number: String){
        uiScope.launch {
            val contentAyat = useCase.getAyat(number)
            contentAyat.collect {
                _observableAyat.value = it
            }
        }
    }

}