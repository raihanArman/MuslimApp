package com.raydev.prayer.di

import com.raydev.prayer.ui.PrayerViewModel
import com.raydev.prayer.work.ReminderHelper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PrayerModule {
    val prayerModule = module {
        viewModel {
            PrayerViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        single {
            ReminderHelper(get())
        }
    }
}