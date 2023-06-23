package com.example.lectus.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.lectus.R
import com.example.lectus.data.Book
import com.example.lectus.data.SearchResponse
import retrofit2.Response
import androidx.compose.runtime.*
import com.example.lectus.screens.BookDetailsScreen

@Composable
fun RecyclerView(add: Boolean, books: State<List<Book>?>, onBookClicked: (Book) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 80.dp)
            .background(colorResource(id = R.color.champagne))
    ) {
        items(items = books.value?: emptyList()) { book ->
            ListItemLibrary(onBookClicked = onBookClicked, book = book, add)
        }
    }
    
}