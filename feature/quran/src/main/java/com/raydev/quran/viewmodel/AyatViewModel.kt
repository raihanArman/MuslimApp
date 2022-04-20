package com.raydev.quran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.quran.GetAyatUseCase
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AyatViewModel(
    private val useCase: GetAyatUseCase
): ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableAyat: LiveData<ResponseState<List<Ayat>>>
        get() = _observableAyat
    private val _observableAyat = MutableLiveData<ResponseState<List<Ayat>>>()


//    val observableCurrentSurah: LiveData<Int>
//        get() = _observableCurrentSurah
//    private val _observableCurrentSurah = MutableLiveData<Int>()


    val surahList = ArrayList<Surah>()
    var currentIndexSurahPager = 0

    fun loadAyat(number: String){
        uiScope.launch {
            val contentAyat = useCase(number)
            contentAyat.collect {
                _observableAyat.value = it
            }
        }
    }

    fun setSurahList(surahList: List<Surah>){
        this.surahList.clear()
        this.surahList.addAll(surahList)
    }

    fun setCurrentSurahPagerIndex(index: Int){
        currentIndexSurahPager = index
    }

//    fun setCurrentSurah(number: Int){
//        _observableCurrentSurah.value = number
//    }

}