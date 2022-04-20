package com.raydev.cache

import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CacheModule {
    val cacheModule = module {
        factory { get<QuranDatabase>().quranDao() }
        single {
            val passphrase = SQLiteDatabase.getBytes("muslimapp".toCharArray())
            val factory = SupportFactory(passphrase)

            Room.databaseBuilder(
                androidContext(),
                QuranDatabase::class.java, "Quran.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }
}