package com.raydev.data.datasource.local

import com.raydev.shared.dao.QuranDao
import com.raydev.shared.entity.AyatEntity
import com.raydev.shared.entity.SurahEntity
import kotlinx.coroutines.flow.Flow

class QuranLocalDataSource(
    private val quranDao: QuranDao
) {
    fun getSurahLocal(): Flow<List<SurahEntity>> = quranDao.getSurahLocal()

    fun getAyatLocal(number: String): Flow<List<AyatEntity>> = quranDao.getAyatLocal(number)

    suspend fun insertSurahLocal(data: List<SurahEntity>) = quranDao.insertSurahLocal(data)

    suspend fun insertAyatLocal(data: List<AyatEntity>) = quranDao.insertAyatLocal(data)
}