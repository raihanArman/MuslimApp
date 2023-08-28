package com.raydev.muslim_app

import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah

class FakeQuranRepositoryTest(
    private val remoteDataSource: QuranRemoteDataSource
) {

//    suspend fun getSurah(): List<Surah> {
//        val response = remoteDataSource.getListSurah()
//        return response
//    }
//
//    suspend fun getAyat(number: String): List<Ayah> {
//        val response = remoteDataSource.getListAyat(number)
//        return response
//    }
}