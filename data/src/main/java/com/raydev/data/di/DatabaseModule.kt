package com.raydev.data.di

import com.raydev.cache.CacheModule
import com.raydev.data.database.QuranDatabase
import com.raydev.shared.database.dao.AyatDao
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.single

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
object DatabaseModule {
    val databaseModule = module {
        single {
            CacheModule.createDatabase(
                context = androidContext(),
                klass = QuranDatabase::class.java
            )
        }

        factory { get<QuranDatabase>().ayatDao() }
        factory { get<QuranDatabase>().ayatLineDao() }
        factory { get<QuranDatabase>().surahDao() }
        factory { get<QuranDatabase>().bookmarkQuran() }
    }
}