package com.raydev.prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.usecase.prayer.GetSholatTimeUseCase
import com.raydev.domain.usecase.prayer.SearchCityUseCase
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.City
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PrayerViewModel(
    private val useCase: SearchCityUseCase,
    private val sholatTimeUseCase: GetSholatTimeUseCase
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

}