package com.raydev.domain.repository

import com.raydev.shared.model.Ayah
import com.raydev.shared.model.QuranLastRead
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
interface LastReadRepository {
    fun setLastRead(ayah: Ayah, surah: Surah, sumAyah: Int)
    fun getLastRead(): Flow<QuranLastRead>
}