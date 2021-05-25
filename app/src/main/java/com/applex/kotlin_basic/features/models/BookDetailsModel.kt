package com.applex.kotlin_basic.features.models

data class BookDetailsModel (
    var book_name : String? = "Name missing",
    var author : String? = "Author missing",
    var category : String? = "Category missing",
    var publish_date : String? = "Publish date missing",
    var page_count : Int? = -1
)