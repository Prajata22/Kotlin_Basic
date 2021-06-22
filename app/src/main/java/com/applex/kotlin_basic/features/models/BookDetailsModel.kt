package com.applex.kotlin_basic.features.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Prajata on 25 May 2021
 */
data class BookDetailsModel(
    @Expose
    @SerializedName("book_name")
    val book_name: String?,

    @Expose
    @SerializedName("author")
    val author: String?,

    @Expose
    @SerializedName("category")
    val category: String?,

    @Expose
    @SerializedName("publish_date")
    val publish_date: String?,

    @Expose
    @SerializedName("page_count")
    val page_count: Int?
)