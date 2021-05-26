package com.applex.kotlin_basic.features.di

import com.applex.kotlin_basic.di.component.ApplicationComponent
import com.applex.kotlin_basic.di.scopes.UserScope
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.features.views.activities.MainActivity
import com.applex.kotlin_basic.features.views.activities.SplashActivity
import com.applex.kotlin_basic.features.views.adapters.BooksAdapter
import com.applex.kotlin_basic.features.views.fragments.BookDetailsFragment
import com.applex.kotlin_basic.features.views.fragments.BooksListFragment
import dagger.Component

/**
 * Created by Prajata on 25 May 2021
 */
@UserScope
@Component(dependencies = [ApplicationComponent::class], modules = [BooksModule::class])
interface BooksComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(booksListFragment: BooksListFragment)

    fun inject(bookDetailsFragment: BookDetailsFragment)

    fun inject(booksAdapter: BooksAdapter)

    fun inject(booksViewModel: BooksViewModel)
}