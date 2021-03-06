package com.applex.kotlin_basic.features.di

import com.applex.kotlin_basic.di.scopes.UserScope
import com.applex.kotlin_basic.features.interfaces.BooksRestApi
import com.applex.kotlin_basic.features.repository.BooksRepository
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by Prajata on 25 May 2021
 */
@Module
class BooksModule {

    @Provides
    @UserScope
    fun provideBooksRestApi(@Named(Constants.RETROFIT_WITHOUT_HEADERS) retrofit: Retrofit): BooksRestApi =
        retrofit.create(BooksRestApi::class.java)

    @Provides
    @UserScope
    fun provideBooksRepository(
        booksRestApi: BooksRestApi, commonUtils: CommonUtils
    ): BooksRepository =
        BooksRepository(booksRestApi, commonUtils)
}