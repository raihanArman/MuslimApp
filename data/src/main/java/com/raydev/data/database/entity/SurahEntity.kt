package com.raydev.shared.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tb_surah")
data class SurahEntity(
    @PrimaryKey
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page:Int,
    val translation: ArrayList<LanguageString>
)