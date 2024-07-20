package com.raydev.quran.ui

import com.raydev.shared.model.Surah
import kotlinx.collections.immutable.ImmutableList

/**
 * @author Raihan Arman
 * @date 10/08/23
 */
data class QuranMainState(
    val listSurah: ImmutableList<Surah> ? = null,
    val isOpenJumpDialog: Boolean = false
)
