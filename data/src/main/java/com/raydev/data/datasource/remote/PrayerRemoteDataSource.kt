package com.raydev.data.datasource.remote

import com.raydev.anabstract.state.ResponseState
import com.raydev.data.network.PrayerService
import com.raydev.shared.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class PrayerRemoteDataSource(
    private val apiService: PrayerService
) {

    fun searchCity(city: String): Flow<ResponseState<List<City>>>{
        return flow {
            emit(ResponseState.Loading())
            try{
                val response = apiService.searchCity(city)
                val data = response.data
                if(data.isNotEmpty()){
                    emit(ResponseState.Success(data))
                }else{
                    emit(ResponseState.Empty)
                }
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}