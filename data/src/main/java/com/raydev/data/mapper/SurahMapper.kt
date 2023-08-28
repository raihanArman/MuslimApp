package com.raydev.data.mapper

import android.content.Context
import com.raydev.shared.database.entity.LanguageString
import com.raydev.shared.database.entity.SurahEntity
import com.raydev.shared.model.Surah
import com.raydev.shared.util.getArabicCalligraphy

/**
 * @author Raihan Arman
 * @date 12/08/23
 */

fun SurahEntity.mapToModel(context: Context) = Surah(
    id = id,
    name = name,
    revelation = revelation,
    verses = verses,
    page = page,
    translation = ArrayList(
        translation.map {
            LanguageString(
                text = it.text ?: "",
                language = it.language ?: ""
            )
        }
    ),
    caligraphy = getArabicCalligraphy(context, id)
)
