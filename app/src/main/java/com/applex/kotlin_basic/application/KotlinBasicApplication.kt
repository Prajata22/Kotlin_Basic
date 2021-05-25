package com.applex.kotlin_basic.application

import androidx.multidex.MultiDexApplication
import com.applex.kotlin_basic.di.component.ApplicationComponent

class KotlinBasicApplication : MultiDexApplication() {

    private lateinit var applicationContext: KotlinBasicApplication
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationContext = this;
    }

    fun app(): KotlinBasicApplication = applicationContext

    fun getApplicationComponent(): ApplicationComponent = applicationComponent
}