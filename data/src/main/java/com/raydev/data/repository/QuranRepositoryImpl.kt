package com.raydev.data.repository

import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.domain.model.Ayat
import com.raydev.domain.model.Surah
import com.raydev.domain.util.ResponseState
import com.raydev.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class QuranRepositoryImpl(
    private val remoteDataSource: QuranRemoteDataSource
): QuranRepository {

    override fun getSurah(): Flow<ResponseState<List<Surah>>> {
        return remoteDataSource.getListSurah()
    }

    override fun getAyat(number: String): Flow<ResponseState<List<Ayat>>> {
        return remoteDataSource.getListAyat(number)
    }
}