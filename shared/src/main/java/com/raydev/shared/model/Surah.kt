package com.raydev.shared.model

import com.raydev.shared.database.entity.LanguageString

data class Surah(
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page: Int,
    val translation: ArrayList<LanguageString>,
    var listAyah: List<Ayah>? = null,
    var ayahCount: Int,
    var caligraphy: String
)
