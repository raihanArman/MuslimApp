package com.raydev.network.di

import com.google.gson.GsonBuilder
import com.raydev.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    val networkModule = module{

        single {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            val clientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }
//            clientBuilder.addInterceptor { chain ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader( //I can't get token because there is no context here.
//                        "Authorization", "Bearer ${PreferencesHelper.getInstance(context).token.toString()}"
//                    )
//                    .build()
//                chain.proceed(newRequest)
//            }



            clientBuilder.readTimeout(120, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
            clientBuilder.build()
        }
        single{
            GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
        }
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .client(get())
                .build()
        }
    }
}