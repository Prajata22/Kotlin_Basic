package com.applex.kotlin_basic.di.module

import android.app.Application
import android.content.Context
import com.applex.kotlin_basic.BuildConfig
import com.applex.kotlin_basic.utils.IntegerTypeAdapter
import com.applex.kotlin_basic.utils.preferences.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule (private val context: Application) {

    @Provides
    @Singleton
    fun provideContext() : Context = context

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): PreferenceManager = PreferenceManager(context)

    @Provides
    @Singleton
    @Named()
    fun provideRetrofit(@Named() gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    @Singleton
    @Named()
    fun provideGoogleGson() : Gson = GsonBuilder().registerTypeAdapter(Int::class.java, IntegerTypeAdapter()).create()
}