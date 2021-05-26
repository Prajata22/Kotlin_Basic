package com.applex.kotlin_basic.application

import androidx.multidex.MultiDexApplication
import com.applex.kotlin_basic.di.component.ApplicationComponent
import com.applex.kotlin_basic.di.component.DaggerApplicationComponent
import com.applex.kotlin_basic.di.module.ApplicationModule

/**
 * Created by Prajata on 25 May 2021
 */
class KotlinBasicApplication : MultiDexApplication() {

    private lateinit var applicationContext: KotlinBasicApplication
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationContext = this
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(
                ApplicationModule(this)
            )
            .build()
    }

    fun app(): KotlinBasicApplication = applicationContext

    fun getApplicationComponent(): ApplicationComponent = applicationComponent
}