package com.raydev.data.datasource.local

import com.raydev.shared.database.dao.SurahDao
import com.raydev.shared.database.entity.SurahEntity

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class SurahLocalDataSource(
    private val surahDao: SurahDao
) {
    fun getSurah() = surahDao.getSurah()
    fun getSurahById(id:Int) = surahDao.getSurahById(id)
    fun getSurahByIdSingle(id:Int) = surahDao.getSurahByIdSingle(id)
    suspend fun saveSurah(book:List<SurahEntity>) = surahDao.saveSurah(book)
    suspend fun deleteSurah() = surahDao.deleteSurah()
}