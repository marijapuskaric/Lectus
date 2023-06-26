package com.example.lectus.data

data class BookData(
    val title: String,
    val authors: ArrayList<String>? = null,
    val description: String? = null,
    val publisher: String? = null,
    val pageCount: Long? = null,
    val image: String? = null,
    val status: String
){
    constructor() : this("", ArrayList(), "", "", 0L, null, "")
}
