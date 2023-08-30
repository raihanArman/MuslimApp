package com.raydev.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

object CacheModule {
    fun <T : RoomDatabase> createDatabase(
        context: Context,
        klass: Class<T>,
    ): T {
        val passphrase = SQLiteDatabase.getBytes("muslimapp".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            klass, "Quran.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}
