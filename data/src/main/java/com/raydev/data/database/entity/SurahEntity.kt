package com.raydev.shared.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_surah")
data class SurahEntity(
    @PrimaryKey
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page: Int,
    val translation: ArrayList<LanguageStringEntity>
)
