package com.raydev.quran.di

import com.raydev.quran.ui.QuranMainViewModel
import com.raydev.quran.work.FileDownloadHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val quranModule = module {
    viewModel {
        QuranMainViewModel(get(), get())
    }

    single {
        FileDownloadHelper(get())
    }
}
