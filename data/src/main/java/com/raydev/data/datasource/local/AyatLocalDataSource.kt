package com.raydev.data.datasource.local

import com.raydev.shared.database.dao.AyatDao
import com.raydev.shared.database.entity.AyatEntity

class AyatLocalDataSource(
    private val ayatDao: AyatDao
) {
    fun getAyahBySurahId(chapterId: Int) = ayatDao.getAyahBySurahId(chapterId)
    fun getAyahBySurahIdAndVerseNumber(chapterId: Int, verseNumber: Int) =
        ayatDao.getAyahBySurahIdAndVerseNumber(chapterId, verseNumber)
    fun getAyah() = ayatDao.getAyah()
    fun searchAyah(query: String) = ayatDao.searchAyah(query)
    suspend fun getAyahCount() = ayatDao.getAyahCount()
    suspend fun getAyahCountBySurahId(surahId: Int) = ayatDao.getAyahCountBySurahId(surahId)
    fun getAyahByJuz(juz: Int, limit: Int) = ayatDao.getAyahByJuz(juz, limit)
    fun getAyahByHizb(hizb: Float, limit: Int) = ayatDao.getAyahByHizb(hizb, limit)
    fun getAyahById(id: Int) = ayatDao.getAyahById(id)

    fun getAyahByVerseNumber(verseNumber: Int) = ayatDao.getAyahByVerseNumber(verseNumber)
    suspend fun saveAyah(book: List<AyatEntity>) = ayatDao.saveAyah(book)
    suspend fun deleteAyah() = ayatDao.deleteAyah()
}
