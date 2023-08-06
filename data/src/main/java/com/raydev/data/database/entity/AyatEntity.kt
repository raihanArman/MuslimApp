package com.raydev.shared.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tb_ayat")
data class AyatEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val verse_number:Int,
    @ColumnInfo(name = "text")
    var text: ArrayList<LanguageString> = ArrayList(),
    @ColumnInfo(name = "juz")
    var juz:Int,
    @ColumnInfo(name = "hizb")
    var hizb:Float,
    @ColumnInfo(name = "use_bismillah")
    var use_bismillah:Boolean?=false,
    @ColumnInfo(name = "chapter_id")
    var chapterId:Int
)