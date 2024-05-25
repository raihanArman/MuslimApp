package com.raydev.dailyduas.presentation

import com.raydev.dailyduas.presentation.viewmodel.DailyDuasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 22/05/24
 */

val dailyDuasPresentationModule = module {
    viewModel {
        DailyDuasViewModel(get())
    }
}
