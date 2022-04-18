package com.raydev.muslim_app

import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeQuranRepositoryTest(
    private val remoteDataSource: QuranRemoteDataSource
): QuranRepository {

    override fun getSurah(): Flow<ResponseState<List<Surah>>> {
        return flow{
            emit(ResponseState.Loading())
            try{
                val response = remoteDataSource.getListSurah()
                emit(ResponseState.Success(response))
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAyat(number: String): Flow<ResponseState<List<Ayat>>> {
        return flow{
            emit(ResponseState.Loading())
            try{
                val response = remoteDataSource.getListAyat(number)
                emit(ResponseState.Success(response))
            }catch (e: Exception){
                emit(ResponseState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}