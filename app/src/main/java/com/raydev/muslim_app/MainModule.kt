package com.raydev.muslim_app

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
object MainModule {
    val mainModule = module {
        viewModel { MainViewModel(get(), get()) }
    }
}