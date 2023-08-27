package com.raydev.data.repository

import android.content.Context
import com.raydev.data.datasource.local.SurahLocalDataSource
import com.raydev.data.datasource.pref.SharedPreferenceSource
import com.raydev.domain.repository.LastReadRepository
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.QuranLastRead
import com.raydev.shared.model.Surah
import com.raydev.shared.util.getArabicCalligraphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
class LastReadRepositoryImpl(
    private val sharedPreferenceSource: SharedPreferenceSource,
): LastReadRepository {

    private val _lastReadFlow: MutableStateFlow<QuranLastRead> = MutableStateFlow(
        sharedPreferenceSource.quranLastRead
    )

    override fun setLastRead(ayah: Ayah, surah: Surah, sumAyah: Int) {
        val data = QuranLastRead(
            surahId = ayah.chapterId,
            ayah = ayah.verseNumber,
            surahText = surah.name,
            surahCalligraphy = surah.caligraphy,
            sumAyah = sumAyah
        )
        sharedPreferenceSource.quranLastRead = data
        _lastReadFlow.value = data
    }

    override fun getLastRead(): Flow<QuranLastRead> = _lastReadFlow

}