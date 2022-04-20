package com.raydev.shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.shared.entity.AyatEntity
import com.raydev.shared.entity.SurahEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {
    @Query("SELECT * FROM surah")
    fun getSurahLocal(): Flow<List<SurahEntity>>

    @Query("SELECT * FROM ayat WHERE nomor = :number")
    fun getAyatLocal(number: String): Flow<List<AyatEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSurahLocal(anime: List<SurahEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAyatLocal(anime: List<AyatEntity>)
}