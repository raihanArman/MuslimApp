package com.rctiplus.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
val mainModule = module {
    viewModel { MainViewModel(get(), get(), get(), get()) }
}
