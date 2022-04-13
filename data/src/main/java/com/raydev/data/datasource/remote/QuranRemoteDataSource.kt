package com.raydev.data.datasource.remote

import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import com.raydev.data.network.QuranService
import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuranRemoteDataSource(
    private val apiService: QuranService,
) {
    fun getListSurah(): Flow<ResponseState<List<Surah>>> {
        return flow{
            emit(ResponseState.Loading())
            try{
                val response = apiService.getListSurah()
                emit(ResponseState.Success(response))
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getListAyat(number: String): Flow<ResponseState<List<Ayat>>> {
        return flow{
            emit(ResponseState.Loading())
            try{
                val response = apiService.getListAyat(number)
                emit(ResponseState.Success(response))
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}