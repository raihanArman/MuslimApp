package com.raydev.shared.model

import androidx.room.PrimaryKey
import com.raydev.shared.database.entity.LanguageString

data class Surah(
    @PrimaryKey
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page: Int,
    val translation: ArrayList<LanguageString>,
    var listAyah: List<Ayah>? = null,
    var caligraphy: String
)
