package com.raydev.data.di

import com.raydev.data.repository.BookmarkRepositoryImpl
import com.raydev.data.repository.LastReadRepositoryImpl
import com.raydev.data.repository.MosqueRepositoryImpl
import com.raydev.data.repository.PrayerRepositoryImpl
import com.raydev.data.repository.QuranRepositoryImpl
import com.raydev.domain.repository.BookmarkRepository
import com.raydev.domain.repository.LastReadRepository
import com.raydev.domain.repository.MosqueRepository
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.repository.QuranRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var repositoryModule = module {
    factory<QuranRepository> {
        QuranRepositoryImpl(
            get(), get(), get(), get(), get(), androidContext()
        )
    }

    single<PrayerRepository> {
        PrayerRepositoryImpl(
            get(),
            get()
        )
    }

    single<BookmarkRepository> {
        BookmarkRepositoryImpl(
            androidContext(),
            get(),
            get(),
            get()
        )
    }

    single<LastReadRepository> {
        LastReadRepositoryImpl(
            get(),
        )
    }

    single<MosqueRepository> {
        MosqueRepositoryImpl(
            get(),
        )
    }
}
