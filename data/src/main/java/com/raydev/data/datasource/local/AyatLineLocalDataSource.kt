package com.raydev.data.datasource.local

import com.raydev.shared.database.dao.AyatLineDao
import com.raydev.shared.database.entity.AyahLine

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
data class AyatLineLocalDataSource(
    private val ayatLineDao: AyatLineDao
) {
    suspend fun saveAyahLine(book:List<AyahLine>) = ayatLineDao.saveAyahLine(book)
    fun getAyahLine() = ayatLineDao.getAyahLine()
    fun getAyahLineByPage(page:Int) = ayatLineDao.getAyahLineByPage(page)
    fun getAyahLineByKey(verseKey:String) = ayatLineDao.getAyahLineByKey(verseKey)
    fun getAyahLineByJuzNumber(juzNumber:Int) = ayatLineDao.getAyahLineByJuzNumber(juzNumber)
    fun getAyahLineByHizbNumber(hizb:Float) = ayatLineDao.getAyahLineByHizbNumber(hizb)
    fun getCount() = ayatLineDao.getCount()
    suspend fun delete() = ayatLineDao.delete()
}