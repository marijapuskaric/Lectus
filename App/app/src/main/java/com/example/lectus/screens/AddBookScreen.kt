package com.example.lectus.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lectus.R
import com.example.lectus.bookapi.BooksRepository
import com.example.lectus.composables.CustomBottomNavigation
import com.example.lectus.composables.Header
import com.example.lectus.composables.TopNavigationBar
import com.example.lectus.viewmodels.SearchBookViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBookScreen(
    mainNavController: NavHostController,
) {
    val repository = BooksRepository()
    val searchBookViewModel = SearchBookViewModel(repository)
    var selectedTopItem by remember { mutableStateOf(0) }
    val itemsTop = listOf(
        stringResource(id = R.string.search),
        stringResource(id = R.string.add_my_book)
    )
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Header()
            TopNavigationBar(
                tabs = itemsTop,
                onTabSelected = { selectedTopItem = it },
                selectedTabIndex = selectedTopItem
            )
            when (selectedTopItem)
            {
                0 -> SearchBookScreen(searchBookViewModel)
                1 -> AddMyBookScreen()
            }
        }
        CustomBottomNavigation(mainNavController = mainNavController)
}