package com.applex.kotlin_basic.di.component

import android.content.Context
import com.applex.kotlin_basic.di.module.ApplicationModule
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.Constants
import com.applex.kotlin_basic.utils.PreferenceManager
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @Named(Constants.RETROFIT_WITHOUT_HEADERS)
    fun provideRetrofit(): Retrofit

    fun provideContext(): Context

    fun provideSharedPreferences(): PreferenceManager

    fun provideCommonUtils() : CommonUtils
}