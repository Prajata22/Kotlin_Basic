package com.applex.kotlin_basic.features.di

import android.content.Context
import com.applex.kotlin_basic.di.scopes.UserScope
import com.applex.kotlin_basic.features.interfaces.BooksRestApi
import com.applex.kotlin_basic.features.repository.BooksRepository
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.Constants
import com.applex.kotlin_basic.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class BooksModule {

    @Provides
    @UserScope
    fun provideBooksRestApi(@Named(Constants.RETROFIT_WITHOUT_HEADERS) retrofit: Retrofit): BooksRestApi =
        retrofit.create(BooksRestApi::class.java)

    @Provides
    @UserScope
    fun provideBooksRepository(
        booksRestApi: BooksRestApi, context: Context,
        preferenceManager: PreferenceManager, commonUtils: CommonUtils
    ): BooksRepository =
        BooksRepository(booksRestApi, context, preferenceManager, commonUtils)
}