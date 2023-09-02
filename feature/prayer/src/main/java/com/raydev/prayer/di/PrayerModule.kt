package com.raydev.prayer.di

import com.raydev.prayer.ui.PrayerViewModel
import com.raydev.prayer.work.PrayerHelper
import com.raydev.prayer.work.ReminderHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 24/08/23
 */
val prayerModule = module {
    single { ReminderHelper(androidContext()) }
    single { PrayerHelper(androidContext()) }
    viewModel { PrayerViewModel(get(), get()) }
}
