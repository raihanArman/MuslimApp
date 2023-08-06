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
    val id:Int,
    @ColumnInfo(name = "words")
    val words: List<AyahLineWord> = arrayListOf(),
    @ColumnInfo(name = "verse_number")
    val verse_number: Int? = null,
    @ColumnInfo(name = "verse_key")
    val verse_key:String?=null,
    @ColumnInfo(name = "juz_number")
    val juz_number: Int? = null,
    @ColumnInfo(name = "page")
    var page:Int?=null,
    @ColumnInfo(name = "hizb_number")
    val hizb_number: Int? = null,
)
data class AyahLineWord(
    val line_number: Int? = null,
    val id: Int? = null,
    val position: Int? = null,
    var verse_key:String?=null,
    val code_v1: String? = null,
    var page:Int?=null,
    var chapter_number:Int,
    val surah:SurahEntity?=null,
    val is_bismillah:Boolean?=false
)