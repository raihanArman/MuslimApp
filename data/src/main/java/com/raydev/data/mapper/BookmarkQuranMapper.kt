package com.raydev.data.mapper

import com.raydev.data.database.entity.BookmarkQuranEntity
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.BookmarkQuran
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
fun BookmarkQuranEntity.mapToModel(ayah: Ayah?, surah: Surah?)= BookmarkQuran(
    id = id ?: 0,
    surah = surah,
    ayah = ayah
)

fun BookmarkQuran.mapToEntity() = BookmarkQuranEntity(
    id = id,
    surahId = surah?.id ?: 0,
    ayahId = ayah?.verseNumber ?: 0
)