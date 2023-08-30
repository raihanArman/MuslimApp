package com.raihanarman.read_quran.di

import com.raihanarman.read_quran.ui.ReadQuranViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
val readQuranModule = module {
    viewModel { params ->
        ReadQuranViewModel(
            surahUseCase = get(),
            ayahBySurah = get(),
            stateHandle = params.get(),
            bookmarkAyahUseCase = get(),
            lastReadRepository = get()
        )
    }
}
