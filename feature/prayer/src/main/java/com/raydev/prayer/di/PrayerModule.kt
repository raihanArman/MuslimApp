package com.raydev.prayer.di

import com.raydev.prayer.PrayerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PrayerModule {
    val prayerModule = module {
        viewModel {
            PrayerViewModel(get(), get())
        }
    }
}