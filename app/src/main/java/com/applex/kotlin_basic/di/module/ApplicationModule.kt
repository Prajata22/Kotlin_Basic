package com.applex.kotlin_basic.di.module

import android.app.Application
import android.content.Context
import com.applex.kotlin_basic.BuildConfig
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.Constants
import com.applex.kotlin_basic.utils.IntegerTypeAdapter
import com.applex.kotlin_basic.utils.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Prajata on 25 May 2021
 */
@Module
class ApplicationModule(private val context: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): PreferenceManager = PreferenceManager(context)

    @Provides
    @Singleton
    @Named(Constants.RETROFIT_WITHOUT_HEADERS)
    fun provideRetrofit(@Named(Constants.GOOGLE_GSON_RETROFIT) gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @Named(Constants.GOOGLE_GSON_RETROFIT)
    fun provideGoogleGson(): Gson =
        GsonBuilder().registerTypeAdapter(Int::class.java, IntegerTypeAdapter()).create()

    @Provides
    @Singleton
    fun provideCommonUtils(context: Context): CommonUtils = CommonUtils(context)
}