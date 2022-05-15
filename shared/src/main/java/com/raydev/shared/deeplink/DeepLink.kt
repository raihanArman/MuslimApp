package com.raydev.shared.deeplink

import com.raydev.shared.deeplink.Feature.LOGIN_FEATURE
import com.raydev.shared.deeplink.Feature.MAIN_FEATURE
import com.raydev.shared.deeplink.Feature.PRAYER_FEATURE
import com.raydev.shared.deeplink.Feature.QURAN_FEATURE

object Feature{
    const val MAIN_FEATURE = "main"
    const val QURAN_FEATURE = "quran"
    const val PRAYER_FEATURE = "prayer"
    const val LOGIN_FEATURE = "login"
}

object AppLink {
    object Login{
        const val LOGIN_LINK = "muslim://${LOGIN_FEATURE}"
    }
    object Main{
        const val MAIN_LINK = "muslim://${MAIN_FEATURE}"
    }
    object QuranFeature{
        const val QURAN_LINK = "muslim://${QURAN_FEATURE}"
    }
    object PrayerFeature{
        const val PRAYER_LINK = "muslim://${PRAYER_FEATURE}"
    }
}