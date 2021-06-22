package com.applex.kotlin_basic.features.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Prajata on 25 May 2021
 */
data class BooksListModel(
    @Expose
    @SerializedName("data")
    val data: ArrayList<BookDetailsModel>
)