package com.raydev.data.mapper

import com.raydev.shared.database.entity.AyatEntity
import com.raydev.shared.database.entity.LanguageString
import com.raydev.shared.database.entity.LanguageStringEntity
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.AyahFromFile

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun AyatEntity.mapToModel(isBookmark: Boolean = false, isLastRead: Boolean = false) = Ayah(
    id = id,
    verseNumber = verseNumber,
    text = ArrayList(
        text.map {
            LanguageString(
                text = it.text ?: "",
                language = it.language ?: ""
            )
        }
    ),
    juz = juz,
    hizb = hizb,
    useBismillah = useBismillah,
    chapterId = chapterId,
    isBookmark = isBookmark,
    isLastRead = isLastRead
)

fun AyahFromFile.mapToEntity() = AyatEntity(
    id = id,
    verseNumber = verseNumber,
    text = ArrayList(
        text.map {
            LanguageStringEntity(
                text = it.text,
                language = it.language
            )
        }
    ),
    juz = juz,
    hizb = hizb,
    useBismillah = useBismillah,
    chapterId = chapterId,
)
