package com.applex.kotlin_basic.features.models

/**
 * Created by Prajata on 25 May 2021
 */
data class BookDetailsModel(
    val book_name: String?,
    val author: String?,
    val category: String?,
    val publish_date: String?,
    val page_count: Int?
)