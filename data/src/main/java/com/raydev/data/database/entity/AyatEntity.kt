package com.raydev.shared.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_ayat")
data class AyatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "verse_number")
    val verseNumber: Int,
    @ColumnInfo(name = "text")
    var text: ArrayList<LanguageStringEntity> = ArrayList(),
    @ColumnInfo(name = "juz")
    var juz: Int,
    @ColumnInfo(name = "hizb")
    var hizb: Float,
    @ColumnInfo(name = "use_bismillah")
    var useBismillah: Boolean? = false,
    @ColumnInfo(name = "chapter_id")
    var chapterId: Int
)
