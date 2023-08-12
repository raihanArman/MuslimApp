package com.raydev.data.mapper

import com.raydev.shared.database.entity.AyatEntity
import com.raydev.shared.database.entity.LanguageString
import com.raydev.shared.model.Ayah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun AyatEntity.mapToModel() = Ayah(
    id = id,
    verseNumber = verse_number,
    text = ArrayList(text.map {
        LanguageString(
            text = it.text ?: "",
            language = it.language ?: ""
        )
    }),
    juz = juz,
    hizb = hizb,
    useBismillah = use_bismillah,
    chapterId = chapterId
)