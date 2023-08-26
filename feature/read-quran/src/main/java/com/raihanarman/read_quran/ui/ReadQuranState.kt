package com.raihanarman.read_quran.ui

import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
data class ReadQuranState(
    val isLoadTabLayout: Boolean = false,
    val tabsSelected: Int ?= null,
    val surahId: Int ?= null,
    val listAyah: List<Ayah> ?= null,
    val listSurah: List<Surah> ?= null,
    val bottomSheetIsOpen: Boolean = false,
    val ayahIndexSelected: Int = -1,
    val ayahSelected: Ayah ?= null,
    val surahSelected: Surah ?= null
)
