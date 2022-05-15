package com.raydev.quran.viewmodel

import android.R.attr
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


import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import com.raydev.anabstract.extention.default
import com.raydev.quran.util.StatusFile


class AyatViewModel(
    private val useCase: GetAyatUseCase
): ViewModel() {
    private val TAG = "AyatViewModel"

    val surahList = ArrayList<Surah>()
    var surahCurrentSelected : Surah? = null
    var currentIndexSurahPager = 0
    var surahCurrentPosition = 0
    var mediaIsPlay = false
    var mediaIsPause = false
    var currentSurahPlayAudio = ""

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableAyat: LiveData<ResponseState<List<Ayat>>>
        get() = _observableAyat
    private val _observableAyat = MutableLiveData<ResponseState<List<Ayat>>>()


    val observableStatusPlayAudioCurrentSurah: LiveData<StatusFile>
        get() = _observableStatusPlayAudioCurrentSurah
    private val _observableStatusPlayAudioCurrentSurah = MutableLiveData<StatusFile>().default(StatusFile.NOT_DOWNLOADED)

//    val observableCurrentSurah: LiveData<Int>
//        get() = _observableCurrentSurah
//    private val _observableCurrentSurah = MutableLiveData<Int>()

    fun playAudioCurrentSurah(statusPlay: StatusFile){
        currentSurahPlayAudio = surahList[surahCurrentPosition].nomor
        statusPlayAudioCurrentSurah(statusPlay)
    }

    fun loadAyat(number: String){
        uiScope.launch {
            val contentAyat = useCase(number)
            contentAyat.collect {
                _observableAyat.value = it
            }
        }
    }

    fun getCurrentSurahAudioAtLocalStorage(): File{
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "${surahList[surahCurrentPosition].nama}.mp3")
    }

    fun checkFileCurrentSurah(): Boolean {
        val file = getCurrentSurahAudioAtLocalStorage()
        Log.d(TAG, "checkFile: ${file.path}")
        return file.exists()
    }

    fun statusPlayAudioCurrentSurah(statusPlay: StatusFile) {
        if (checkFileCurrentSurah()) {
            if (currentSurahPlayAudio == surahList[surahCurrentPosition].nomor) {
                _observableStatusPlayAudioCurrentSurah.postValue(statusPlay)
            }
        }
    }

    fun setInitPlayAudioCurrentSurah(){
        if(checkFileCurrentSurah()){
            if(!mediaIsPlay){
                _observableStatusPlayAudioCurrentSurah.postValue(StatusFile.NOT_PLAY)
            }else{
                if(mediaIsPause){
                    _observableStatusPlayAudioCurrentSurah.postValue(StatusFile.PAUSE)
                }else{
                    _observableStatusPlayAudioCurrentSurah.postValue(StatusFile.PLAYING)
                }
            }
        }else{
            _observableStatusPlayAudioCurrentSurah.postValue(StatusFile.NOT_DOWNLOADED)
        }

        if(_observableStatusPlayAudioCurrentSurah.value == StatusFile.NOT_PLAY){
            _observableStatusPlayAudioCurrentSurah.postValue(StatusFile.NOT_DOWNLOADED)
        }
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