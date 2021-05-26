package com.applex.kotlin_basic.features.repository

import androidx.lifecycle.MutableLiveData
import com.applex.kotlin_basic.features.interfaces.BooksRestApi
import com.applex.kotlin_basic.features.models.BooksListModel
import com.applex.kotlin_basic.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksRepository(
    private val booksRestApi: BooksRestApi,
    private val commonUtils: CommonUtils
) {
    fun getBooksList(data: MutableLiveData<BooksListModel>, error: MutableLiveData<Throwable>) {
        when {
            commonUtils.checkInternetConnection() -> {
                booksRestApi.getBooksList().enqueue(object : Callback<BooksListModel?> {
                    override fun onResponse(
                        call: Call<BooksListModel?>,
                        response: Response<BooksListModel?>
                    ) = when {
                        response.isSuccessful -> data.value = response.body()
                        else -> error.value = null
                    }

                    override fun onFailure(
                        call: Call<BooksListModel?>,
                        t: Throwable
                    ) {
                        error.value = t
                    }
                })
            }
            else -> {
                error.value = null
            }
        }
    }
}