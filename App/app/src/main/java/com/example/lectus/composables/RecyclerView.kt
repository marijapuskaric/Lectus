package com.example.lectus.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lectus.data.Book
import com.example.lectus.data.BookData

@Composable
fun <T> RecyclerView(
    add: Boolean,
    edit: Boolean,
    books: List<T>?,
    onBookClicked: (T) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(items = books?: emptyList()) { book ->
            when (book) {
                is BookData -> {
                    ListItemLibrary(
                        onBookClicked = {onBookClicked(book)},
                        book = book,
                        title = book.title,
                        authors = book.authors,
                        publisher = book.publisher,
                        description = book.description,
                        pageCount = book.pageCount,
                        image = book.image,
                        add = add,
                        edit = edit
                    )
                }

                is Book -> {
                    ListItemLibrary(
                        onBookClicked = {onBookClicked(book)},
                        book = book,
                        title = book.volumeInfo.title,
                        authors = book.volumeInfo.authors,
                        publisher = book.volumeInfo.publisher,
                        description = book.volumeInfo.description,
                        pageCount = book.volumeInfo.pageCount?.toLong(),
                        image = book.volumeInfo.imageLinks?.thumbnail,
                        add = add,
                        edit = edit
                    )
                }
            }
        }

    }
}


