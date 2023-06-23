package com.example.lectus.data

data class LibraryBook(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val description: String,
    val pageCount: Int,
    val imageLinks: ImageLinks?,
    val status: String
)
