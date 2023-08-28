package com.raydev.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.shared.database.entity.AyahLine
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Dao
interface AyatLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAyahLine(book: List<AyahLine>)

    @Query("SELECT * FROM ayahline")
    fun getAyahLine(): Flow<List<AyahLine>>

    @Query("SELECT * FROM ayahline WHERE page IS :page")
    fun getAyahLineByPage(page: Int): Flow<List<AyahLine>>

    @Query("SELECT * FROM ayahline WHERE verse_key IS :verseKey")
    fun getAyahLineByKey(verseKey: String): Flow<List<AyahLine>>

    @Query("SELECT * FROM ayahline WHERE juz_number IS :juzNumber")
    fun getAyahLineByJuzNumber(juzNumber: Int): Flow<List<AyahLine>>

    @Query("SELECT * FROM ayahline WHERE hizb_number IS :hizb")
    fun getAyahLineByHizbNumber(hizb: Float): Flow<List<AyahLine>>

    @Query("SELECT COUNT() FROM ayahline")
    fun getCount(): Flow<Int>

    @Query("DELETE FROM ayahline")
    suspend fun delete()
}
