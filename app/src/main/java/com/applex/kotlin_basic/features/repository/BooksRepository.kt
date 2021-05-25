package com.applex.kotlin_basic.features.repository

import android.content.Context
import com.applex.kotlin_basic.features.interfaces.BooksRestApi
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.PreferenceManager

class BooksRepository (
        private val booksRestApi: BooksRestApi,
        private val context: Context,
        private val preferenceManager: PreferenceManager,
        private val commonUtils: CommonUtils
) {

}