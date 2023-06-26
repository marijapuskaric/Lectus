package com.example.lectus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.lectus.R
import com.example.lectus.composables.Header
import com.example.lectus.composables.RecyclerView
import com.example.lectus.composables.TopNavigationBar
import com.example.lectus.data.BookData
import com.example.lectus.db.getBooksFromFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MyLibraryScreen() {
    val db = Firebase.firestore
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    var selectedTopItem by remember { mutableStateOf(0) }
    val itemsTop = listOf(
        stringResource(id = R.string.all),
        stringResource(id = R.string.to_read),
        stringResource(id = R.string.reading),
        stringResource(id = R.string.finished_reading)
    )
    val bookListState = remember { mutableStateOf<List<BookData>>(emptyList()) }
    var selectedBook by remember { mutableStateOf<BookData?>(null) }

    if (currentUserUid != null) {
        getBooksFromFirestore(db, currentUserUid) { books ->
            bookListState.value = books
        }
    }
    val filteredBooks = when (selectedTopItem) {
        0 -> bookListState.value
        1 -> bookListState.value.filter { it.status == stringResource(id = R.string.to_read) }
        2 -> bookListState.value.filter { it.status == stringResource(id = R.string.reading) }
        3 -> bookListState.value.filter { it.status == stringResource(id = R.string.finished_reading) }
        else -> bookListState.value
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header()
        TopNavigationBar(tabs = itemsTop, onTabSelected = {selectedTopItem = it}, selectedTabIndex = selectedTopItem)
        Column(Modifier.weight(1f)) {
            if (selectedBook != null) {
                BookDetailsScreen(book = selectedBook!!, navigateBack = { selectedBook = null }, readingStatus = true)
            } else {
                RecyclerView(add = false, edit = true, filteredBooks) { book ->
                    selectedBook = book
                }
            }
        }
    }
}