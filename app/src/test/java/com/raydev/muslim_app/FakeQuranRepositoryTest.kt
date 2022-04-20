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
) {

    suspend fun getSurah(): List<Surah> {
        val response = remoteDataSource.getListSurah()
        return response
    }

    suspend fun getAyat(number: String): List<Ayat> {
        val response = remoteDataSource.getListAyat(number)
        return response
    }
}