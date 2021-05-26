package com.applex.kotlin_basic.features.interfaces

import com.applex.kotlin_basic.features.models.BooksListModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Prajata on 25 May 2021
 */
interface BooksRestApi {

    @GET("a0528e65-80c9-4172-9231-876a622f25ef")
    fun getBooksList(): Call<BooksListModel>
}