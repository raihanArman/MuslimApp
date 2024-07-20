package com.raydev.shared.model

import androidx.compose.runtime.Immutable
import com.raydev.shared.database.entity.LanguageString

@Immutable
data class Surah(
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page: Int,
    val translation: ArrayList<LanguageString>,
    var listAyah: List<Ayah>? = null,
    var caligraphy: String
)
