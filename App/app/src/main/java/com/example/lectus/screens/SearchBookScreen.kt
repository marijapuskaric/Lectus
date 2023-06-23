package com.example.lectus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lectus.R
import com.example.lectus.composables.RecyclerView
import com.example.lectus.data.Book
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.SearchBookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookScreen(searchBookViewModel: SearchBookViewModel = hiltViewModel<SearchBookViewModel>(), navController: NavController) {

    var search by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val books = searchBookViewModel.books.observeAsState()

    var previousSearch by remember { mutableStateOf("") }
    val isSearchChanged = search != previousSearch
    previousSearch = search

    var selectedBook by remember { mutableStateOf<Book?>(null) }


    if (isSearchChanged) {
        selectedBook = null
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.champagne))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.tan))
        ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.search),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = getFontFamily(),
                                color = colorResource(id = R.color.caput_mortuum)
                            )
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = colorResource(id = R.color.champagne),
                        unfocusedBorderColor = colorResource(id = R.color.caput_mortuum),
                        focusedBorderColor = colorResource(id = R.color.caput_mortuum)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(fontSize = 12.sp, color = colorResource(id = R.color.caput_mortuum), fontFamily = getFontFamily()),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            searchBookViewModel.getBooksList(search)

                        }
                    )
                )
        }
        Column(Modifier.weight(1f)) {
            if (selectedBook != null) {
                BookDetailsScreen(book = selectedBook!!, navigateBack = { selectedBook = null })
            } else {
                RecyclerView(true, books) { book ->
                    selectedBook = book
                }
            }
        }
    }
    
}


