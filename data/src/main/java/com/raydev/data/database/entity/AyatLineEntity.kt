package com.raydev.shared.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Entity(tableName = "ayahLine")
data class AyahLine(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "words")
    val words: List<AyahLineWord> = arrayListOf(),
    @ColumnInfo(name = "verse_number")
    val verseNumber: Int? = null,
    @ColumnInfo(name = "verse_key")
    val verseKey: String? = null,
    @ColumnInfo(name = "juz_number")
    val juzNumber: Int? = null,
    @ColumnInfo(name = "page")
    var page: Int? = null,
    @ColumnInfo(name = "hizb_number")
    val hizbNumber: Int? = null,
)
data class AyahLineWord(
    val lineNumber: Int? = null,
    val id: Int? = null,
    val position: Int? = null,
    var verseKey: String? = null,
    val codeV1: String? = null,
    var page: Int? = null,
    var chapterNumber: Int,
    val surah: SurahEntity? = null,
    val isBismillah: Boolean? = false
)
