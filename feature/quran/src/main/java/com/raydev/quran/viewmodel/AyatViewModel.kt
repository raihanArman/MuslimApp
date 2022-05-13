package com.raydev.quran.viewmodel

import android.content.Context
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
    var currentIndexSurahPager = 0

    fun loadAyat(number: String){
        uiScope.launch {
            val contentAyat = useCase(number)
            contentAyat.collect {
                _observableAyat.value = it
            }
        }
    }

    fun checkFile(position: Int, context: Context) {
        val filePath = context.getExternalFilesDir(null)?.absolutePath?.plus("/sample-").plus(surahList[position].nama).plus(".mp3")
        val file = File(filePath)
        _observableDownload.postValue(file.exists())
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