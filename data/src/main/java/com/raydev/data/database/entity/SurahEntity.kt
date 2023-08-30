package com.raydev.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raydev.shared.database.entity.LanguageStringEntity

@Entity(tableName = "tb_surah")
data class SurahEntity(
    @PrimaryKey
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page: Int,
    var ayahCount: Int,
    val translation: ArrayList<LanguageStringEntity>
)
