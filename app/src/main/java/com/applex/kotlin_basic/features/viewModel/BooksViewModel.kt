package com.applex.kotlin_basic.features.viewModel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.applex.kotlin_basic.features.models.BooksListModel
import com.applex.kotlin_basic.features.repository.BooksRepository

import javax.inject.Inject

class BooksViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var booksRepository: BooksRepository

    private val errorData: MutableLiveData<Throwable> = MutableLiveData()

    var bookName: String? = ""
    var author: String? = ""
    var category: String? = ""
    var publishDate: String? = ""
    var pageCount: Int? = -1

    fun getBooksList(): MutableLiveData<BooksListModel> {
        val successData: MutableLiveData<BooksListModel> = MutableLiveData()
        booksRepository.getBooksList(successData, errorData)
        return successData
    }

    fun getErrorData(): MutableLiveData<Throwable> = errorData
}