package com.raydev.quran.ui

import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
data class QuranMainState(
    val listSurah: List<Surah> ?= null
)
