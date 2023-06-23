package com.example.lectus.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lectus.R
import com.example.lectus.bookapi.BooksRepository
import com.example.lectus.composables.Header
import com.example.lectus.getFontFamily
import com.example.lectus.viewmodels.SearchBookViewModel


sealed class TopNavigationScreens(val route: String, @StringRes val resourceId: Int) {
    object Search : TopNavigationScreens("Search", R.string.search_route)
    object AddMyBook : TopNavigationScreens("AddMyBook", R.string.add_my_book_route)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBookScreen() {
    val repository = BooksRepository()
    val navController = rememberNavController()
    val searchBookViewModel = SearchBookViewModel(repository)

    val topNavigationItems = listOf(
        TopNavigationScreens.Search,
        TopNavigationScreens.AddMyBook,
    )
    Column() {
        Header()
        Scaffold(
            topBar = {
                TopNavigation(navController, topNavigationItems)
            },
        ) {
            Column(Modifier.fillMaxSize()) {
                AddBookScreenNavigationConfigurations(navController, searchBookViewModel)
            }
        }
    }

}

@Composable
private fun AddBookScreenNavigationConfigurations(
    navController: NavHostController, searchBookViewModel: SearchBookViewModel
) {
    NavHost(navController, startDestination = TopNavigationScreens.Search.route) {
        composable(TopNavigationScreens.Search.route) {
            SearchBookScreen(searchBookViewModel, navController)
        }
        composable(TopNavigationScreens.AddMyBook.route) {
            AddMyBookScreen()
        }
    }
}


@Composable
private fun TopNavigation(
    navController: NavHostController,
    items: List<TopNavigationScreens>
) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
        ) {
            NavigationBar(containerColor = colorResource(id = R.color.redwood)
            ) {
                val currentRoute = currentRoute(navController)
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {},
                        label = {
                            Text(stringResource(id = screen.resourceId),
                                fontFamily = getFontFamily(),
                                color = colorResource(id = R.color.champagne),
                                modifier = Modifier.padding(top = 2.dp))
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(id = R.color.champagne)
                        )

                    )
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}