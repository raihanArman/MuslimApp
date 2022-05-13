package com.raydev.data.repository

import android.util.Log
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import com.raydev.anabstract.state.ResponseState
import com.raydev.anabstract.util.NetworkBoundResource
import com.raydev.data.datasource.local.QuranLocalDataSource
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.entity.SurahEntity
import com.raydev.shared.mapper.AyatMapper
import com.raydev.shared.mapper.SurahMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class QuranRepositoryImpl(
    private val remoteDataSource: QuranRemoteDataSource,
    private val localDataSource: QuranLocalDataSource,
): QuranRepository {

    override fun getSurah(): Flow<ResponseState<List<Surah>>> =
        object : NetworkBoundResource<List<Surah>, List<Surah>>() {
            override fun loadFromDB(): Flow<List<Surah>> {
                return localDataSource.getSurahLocal().map {
                    SurahMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Surah>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ResponseState<List<Surah>>> {
                return remoteDataSource.getSurah()
            }

            override suspend fun saveCallResult(data: List<Surah>) {
                val animeList = SurahMapper.mapResponseToEntities(data)
                localDataSource.insertSurahLocal(animeList)
            }
        }.asFlow()


    override fun getAyat(number: String): Flow<ResponseState<List<Ayat>>> {
        return remoteDataSource.getListAyat(number)
    }

}