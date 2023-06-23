package com.example.lectus.data

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val description: String,
    val pageCount: Int,
    val imageLinks: ImageLinks?
)
