package com.raydev.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raydev.shared.dao.QuranDao
import com.raydev.shared.entity.AyatEntity
import com.raydev.shared.entity.SurahEntity

@Database(
    entities = [
    SurahEntity::class,
    AyatEntity::class], version = 2, exportSchema = false
)

abstract class QuranDatabase : RoomDatabase() {

    abstract fun quranDao(): QuranDao

}