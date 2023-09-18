package com.raydev.network.di

import com.google.gson.GsonBuilder
import com.raydev.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        clientBuilder.build()
    }
    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }
}
