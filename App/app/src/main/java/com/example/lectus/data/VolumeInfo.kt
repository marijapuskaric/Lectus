package com.example.lectus.data

data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val imageLinks: ImageLinks? = null
)
