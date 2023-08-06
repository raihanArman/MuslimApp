package com.raydev.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.shared.database.entity.AyatEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Dao
interface AyatDao {
    @Query("SELECT * FROM tb_ayat WHERE chapter_id IS :chapterId")
    fun getAyahBySurahId(chapterId:Int): Flow<List<AyatEntity>>

    @Query("SELECT * FROM tb_ayat WHERE chapter_id IS :chapterId AND verse_number IS :verseNumber")
    fun getAyahBySurahIdAndVerseNumber(chapterId: Int, verseNumber:Int): Flow<List<AyatEntity>>

    @Query("SELECT * FROM tb_ayat")
    fun getAyah(): Flow<List<AyatEntity>>

    @Query("SELECT * FROM tb_ayat WHERE text LIKE :query")
    fun searchAyah(query:String): Flow<List<AyatEntity>>

    @Query("Select count() from tb_ayat")
    suspend fun getAyahCount(): Int

    @Query("SELECT * FROM tb_ayat WHERE juz IS :juz LIMIT :limit")
    fun getAyahByJuz(juz:Int, limit:Int): Flow<List<AyatEntity>>

    @Query("SELECT * FROM tb_ayat WHERE hizb IS :hizb LIMIT :limit")
    fun getAyahByHizb(hizb:Float, limit:Int): Flow<List<AyatEntity>>

    @Query("SELECT * FROM tb_ayat WHERE id IS :id")
    fun getAyahById(id:Int): Flow<AyatEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAyah(book:List<AyatEntity>)

    @Query("DELETE FROM tb_ayat")
    suspend fun deleteAyah()
}