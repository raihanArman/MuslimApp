package com.raydev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raydev.data.database.converter.AyahLineWordConverter
import com.raydev.data.database.converter.LanguageStringConverter
import com.raydev.data.database.dao.BookmarkQuranDao
import com.raydev.data.database.entity.BookmarkQuranEntity
import com.raydev.shared.database.dao.AyatDao
import com.raydev.shared.database.dao.AyatLineDao
import com.raydev.shared.database.dao.SurahDao
import com.raydev.shared.database.entity.AyahLine
import com.raydev.shared.database.entity.AyatEntity
import com.raydev.shared.database.entity.SurahEntity

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Database(
    entities = [
        SurahEntity::class,
        AyatEntity::class,
        AyahLine::class,
        BookmarkQuranEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(LanguageStringConverter::class, AyahLineWordConverter::class)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun surahDao(): SurahDao
    abstract fun ayatDao(): AyatDao
    abstract fun ayatLineDao(): AyatLineDao
    abstract fun bookmarkQuran(): BookmarkQuranDao
}