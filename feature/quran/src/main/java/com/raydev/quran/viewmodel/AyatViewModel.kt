package com.raydev.quran.viewmodel

import android.content.Context
import android.os.Environment
import android.util.Log
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
import java.io.File

class AyatViewModel(
    private val useCase: GetAyatUseCase
): ViewModel() {
    private val TAG = "AyatViewModel"
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableAyat: LiveData<ResponseState<List<Ayat>>>
        get() = _observableAyat
    private val _observableAyat = MutableLiveData<ResponseState<List<Ayat>>>()

    val observableDownload: LiveData<Boolean>
        get() = _observableDownload
    private val _observableDownload = MutableLiveData<Boolean>()


//    val observableCurrentSurah: LiveData<Int>
//        get() = _observableCurrentSurah
//    private val _observableCurrentSurah = MutableLiveData<Int>()


    val surahList = ArrayList<Surah>()
    var surahCurrentSelected : Surah? = null
    var currentIndexSurahPager = 0
    var surahCurrentPosition = 0

    fun loadAyat(number: String){
        uiScope.launch {
            val contentAyat = useCase(number)
            contentAyat.collect {
                _observableAyat.value = it
            }
        }
    }

    fun checkFileCurrentSurah() {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "${surahList[surahCurrentPosition].nama}.mp3")
        Log.d(TAG, "checkFile: ${file.path}")
        _observableDownload.postValue(file.exists())
    }

    fun setSurahList(surahList: List<Surah>){
        this.surahList.clear()
        this.surahList.addAll(surahList)
    }

    fun setCurrentSurahPagerIndex(index: Int){
        currentIndexSurahPager = index
    }

    fun setCurrentSurah(number: Int){
        surahCurrentSelected = surahList[number]
        surahCurrentPosition = number
    }

}