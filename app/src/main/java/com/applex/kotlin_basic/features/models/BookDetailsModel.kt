package com.applex.kotlin_basic.features.models

data class BookDetailsModel (
    var book_name : String? = "",
    var author : String? = "",
    var category : String? = "",
    var publish_date : String? = "",
    var page_count : Int? = -1
)