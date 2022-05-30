package com.raydev.prayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.prayer.*
import com.raydev.prayer.work.ReminderHelper
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.City
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrayerViewModel(
    private val useCase: SearchCityUseCase,
    private val sholatTimeUseCase: GetSholatTimeUseCase,
    private val setImsakDataUseCase: SetImsakDataUseCase,
    private val setSubuhDataUseCase: SetSubuhDataUseCase,
    private val setDhuhurDataUseCase: SetDhuhurDataUseCase,
    private val setAsharDataUseCase: SetAsharDataUseCase,
    private val setMaghribDataUseCase: SetMaghribDataUseCase,
    private val setIsyaDataUseCase: SetIsyaDataUseCase,
    private val getImsakDataUseCase: GetImsakDataUseCase,
    private val getSubuhDataUseCase: GetSubuhDataUseCase,
    private val getDhuhurDataUseCase: GetDhuhurDataUseCase,
    private val getAsharDataUseCase: GetAsharDataUseCase,
    private val getMaghribDataUseCase: GetMaghribDataUseCase,
    private val getIsyaDataUseCase: GetIsyaDataUseCase,
    private val reminderHelper: ReminderHelper
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val observableSearchCity: LiveData<ResponseState<List<City>>>
        get() = _observableSearchCity
    private val _observableSearchCity = MutableLiveData<ResponseState<List<City>>>()


    val observableGetSholatTime: LiveData<ResponseState<SholatTime>>
        get() = _observableGetSholatTime
    private val _observableGetSholatTime = MutableLiveData<ResponseState<SholatTime>>()

    fun searchCity(city: String){
        uiScope.launch {
            val result = useCase(city)
            result.collect {
                _observableSearchCity.postValue(it)
            }
        }
    }

    fun getSholatTime(cityId: String, date: String){
        uiScope.launch {
            val result = sholatTimeUseCase(cityId, date)
            result.collect {
                _observableGetSholatTime.postValue(it)
            }
        }
    }

    private fun setReminder(time: String, checked: Boolean){
        val data = time.split(":")
        val hour = data[0].toInt()
        val minute = data[1].toInt()
        reminderHelper.enableReminder(hour, minute, 0, checked)
    }

    fun setImsakData(time: String, status: Boolean){
        uiScope.launch {
            setImsakDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    fun setSubuhData(time: String, status: Boolean){
        uiScope.launch {
            setSubuhDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    fun setDhuhurData(time: String, status: Boolean){
        uiScope.launch {
            setDhuhurDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    fun setAsharData(time: String, status: Boolean){
        uiScope.launch {
            setAsharDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    fun setMaghribData(time: String, status: Boolean){
        uiScope.launch {
            setMaghribDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    fun setIsyaData(time: String, status: Boolean){
        uiScope.launch {
            setIsyaDataUseCase(PrayerData(time, status))
            setReminder(time, status)
        }
    }

    val getImsakData = getImsakDataUseCase()
    val getSubuhData = getSubuhDataUseCase()
    val getDhuhurData = getDhuhurDataUseCase()
    val getAsharData = getAsharDataUseCase()
    val getMaghribData = getMaghribDataUseCase()
    val getIsyaData = getIsyaDataUseCase()

}