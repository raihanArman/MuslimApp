package com.raydev.shared.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.shared.database.entity.SurahEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Dao
interface SurahDao {
    @Query("SELECT * FROM tb_surah")
    fun getSurah(): Flow<List<SurahEntity>>
    @Query("SELECT * FROM tb_surah WHERE id is :id")
    fun getSurahById(id:Int): Flow<List<SurahEntity>>
    @Query("SELECT * FROM tb_surah WHERE id is :id")
    fun getSurahByIdSingle(id:Int): Flow<SurahEntity?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSurah(book:List<SurahEntity>)
    @Query("DELETE FROM tb_surah")
    suspend fun deleteSurah()
}