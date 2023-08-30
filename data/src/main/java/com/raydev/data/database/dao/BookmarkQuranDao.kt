package com.raydev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.data.database.entity.BookmarkQuranEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Dao
interface BookmarkQuranDao {
    @Query("SELECT * FROM bookmark_quran")
    fun getBookmarks(): Flow<List<BookmarkQuranEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(bookmark: BookmarkQuranEntity)
    @Query("DELETE FROM bookmark_quran WHERE :surahId = surahId AND :ayahId = ayahId")
    suspend fun deleteBookmark(surahId: Int, ayahId: Int)
    @Query("SELECT * FROM bookmark_quran WHERE :surahId = surahId AND :ayahId = ayahId")
    fun getBookmarkBySurahAndAyah(surahId: Int, ayahId: Int): BookmarkQuranEntity?
}
