package com.raydev.shared.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
    @SerializedName("line_number")
    val lineNumber: Int? = null,
    val id: Int? = null,
    val position: Int? = null,
    @SerializedName("verse_key")
    var verseKey: String? = null,
    @SerializedName("code_v1")
    val codeV1: String? = null,
    var page: Int? = null,
    @SerializedName("chapter_number")
    var chapterNumber: Int,
    val surah: SurahEntity? = null,
    @SerializedName("is_bismillah")
    val isBismillah: Boolean? = false
)
