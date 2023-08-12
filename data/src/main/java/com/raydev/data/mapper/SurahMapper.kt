package com.raydev.data.mapper

import com.raydev.shared.database.entity.SurahEntity
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun SurahEntity.mapToModel() = Surah(
    id = id,
    name = name,
    revelation = revelation,
    verses = verses,
    page = page
)