package com.example.lectus.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.lectus.R
import com.example.lectus.bookapi.BooksRepository
import com.example.lectus.composables.Header
import com.example.lectus.composables.TopNavigationBar
import com.example.lectus.viewmodels.SearchBookViewModel


@Composable
fun AddBookScreen() {
    val repository = BooksRepository()
    val searchBookViewModel = SearchBookViewModel(repository)
    var selectedTopItem by remember { mutableStateOf(0) }
    val itemsTop = listOf(
        stringResource(id = R.string.search),
        stringResource(id = R.string.add_my_book)
    )

    Column {
        Header()
        TopNavigationBar(tabs = itemsTop, onTabSelected = {selectedTopItem = it}, selectedTabIndex = selectedTopItem)
        when (selectedTopItem) {
            0 -> SearchBookScreen(searchBookViewModel)
            1 -> AddMyBookScreen()
        }
    }

}