package com.raydev.data.datasource.remote

import com.raydev.shared.model.Ayah
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
    fun getSurah(): Flow<ResponseState<List<Surah>>> {
        return flow {
            emit(ResponseState.Loading())
            try {
                val response = apiService.getListSurah()
                val data = response
                if (data.isNotEmpty()) {
                    emit(ResponseState.Success(response))
                } else {
                    emit(ResponseState.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getListAyat(number: String): Flow<ResponseState<List<Ayah>>>  {
        return flow {
            emit(ResponseState.Loading())
            try {
                val response = apiService.getListAyat(number)
                val data = response
                if (data.isNotEmpty()) {
                    emit(ResponseState.Success(response))
                } else {
                    emit(ResponseState.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}