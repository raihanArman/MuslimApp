package com.raihanarman.bookmark.di

import com.raihanarman.bookmark.ui.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
val bookmarkModule = module {
    viewModel {
        BookmarkViewModel(get(), get())
    }
}