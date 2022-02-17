package com.hieubm.contact.injection.module

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hieubm.contact.BuildConfig
import com.hieubm.core.data.remote.api.ContactService
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCache(application: Application): Cache {
        val maxSize = 32 * 1024 * 1024L     // 32 MB
        return Cache(application.cacheDir, maxSize)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): LoggingInterceptor {
        val level = if (BuildConfig.DEBUG) Level.BASIC else Level.NONE
        return LoggingInterceptor.Builder()
            .setLevel(level)
            .log(Log.INFO)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideContactService(retrofit: Retrofit): ContactService {
        return retrofit.create(ContactService::class.java)
    }
}