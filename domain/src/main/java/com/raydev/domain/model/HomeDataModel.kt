package com.raydev.domain.model

import com.raydev.anabstract.state.ResponseState
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.QuranLastRead

/**
 * @author Raihan Arman
 * @date 19/09/23
 */
data class HomeDataModel(
    val location: String? = null,
    val prayerTime: PrayerTime? = null,
    val nextPrayerTime: NextPrayerTime? = null,
    val hijrDate: String? = null,
    val currentdate: String? = null,
    val lastRead: QuranLastRead? = null,
    val nearbyMosque: ResponseState<MosqueModel?> ? = null
)
