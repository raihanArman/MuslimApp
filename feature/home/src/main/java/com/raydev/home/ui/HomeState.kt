package com.raydev.home.ui

import com.raydev.shared.model.PrayerTime

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
data class HomeState(
    val location: String? = null,
    val prayerTime: PrayerTime ?= null
)