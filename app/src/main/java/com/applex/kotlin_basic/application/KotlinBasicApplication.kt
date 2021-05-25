package com.applex.kotlin_basic.application

import androidx.multidex.MultiDexApplication

class KotlinBasicApplication : MultiDexApplication() {

    private lateinit var applicationContext : KotlinBasicApplication
    private lateinit var applicationComponent: ApplicationComponent

    fun app() : KotlinBasicApplication {
        return applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        applicationContext = this;

    }

    fun getApplicationComponent() : ApplicationComponent {
        return applicationComponent
    }
}