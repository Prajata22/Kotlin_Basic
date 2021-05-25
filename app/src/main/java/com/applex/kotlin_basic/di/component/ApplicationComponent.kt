package com.applex.kotlin_basic.di.component

import android.content.Context
import com.applex.kotlin_basic.di.module.ApplicationModule
import com.applex.kotlin_basic.utils.Constants
import com.applex.kotlin_basic.utils.preferences.PreferenceManager
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @Named()
    fun provideRetrofit() : Retrofit

    fun provideContext() : Context

    fun provideSharedPreferences() : PreferenceManager
}