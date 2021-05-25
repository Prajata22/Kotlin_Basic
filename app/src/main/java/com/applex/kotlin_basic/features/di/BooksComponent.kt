package com.applex.kotlin_basic.features.di

import com.applex.kotlin_basic.di.component.ApplicationComponent
import com.applex.kotlin_basic.di.scopes.UserScope
import dagger.Component

@UserScope
@Component(dependencies = [ApplicationComponent::class], modules = [BooksModule::class])
interface BooksComponent {

    fun inject(temp_obj : Any)
}