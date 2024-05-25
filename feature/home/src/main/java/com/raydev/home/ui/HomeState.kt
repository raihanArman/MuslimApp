package com.raydev.home.ui

import com.raydev.domain.model.MosqueData
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.QuranLastRead

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
data class HomeState(
    val location: String? = null,
    val prayerTime: PrayerTime ? = null,
    val nextPrayerTime: NextPrayerTime ? = null,
    val hijrDate: String? = null,
    val currentdate: String? = null,
    val lastRead: QuranLastRead ? = null,
    val mosqueData: List<MosqueData> ? = null,
    val isLoading: Boolean = false
)
