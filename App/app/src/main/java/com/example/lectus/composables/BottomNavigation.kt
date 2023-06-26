package com.example.lectus.composables

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lectus.R
import com.example.lectus.getFontFamily
import com.example.lectus.screens.AddBookScreen
import com.example.lectus.screens.GoalsScreen
import com.example.lectus.screens.MyLibraryScreen
import com.example.lectus.screens.SettingsScreen
import com.example.lectus.viewmodels.ThemeViewModel


sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object MyLibrary : BottomNavigationScreens("MyLibrary", R.string.my_library_route, Icons.Filled.LibraryBooks)
    object AddBook : BottomNavigationScreens("AddBook", R.string.add_book_route, Icons.Filled.Add)
    object Goals : BottomNavigationScreens("Goals", R.string.goals_route, Icons.Filled.AccessAlarm)
    object Settings : BottomNavigationScreens("Settings", R.string.settings_route, Icons.Filled.Settings)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(themeViewModel: ThemeViewModel, mainNavController: NavHostController) {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.MyLibrary,
        BottomNavigationScreens.AddBook,
        BottomNavigationScreens.Goals,
        BottomNavigationScreens.Settings
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, bottomNavigationItems)
        },
    ) {
        Column(Modifier.fillMaxSize()) {
            MainScreenNavigationConfigurations(navController, themeViewModel, mainNavController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    mainNavController: NavHostController,
) {

    NavHost(navController, startDestination = BottomNavigationScreens.MyLibrary.route) {
        composable(BottomNavigationScreens.MyLibrary.route) {
            MyLibraryScreen()
        }
        composable(BottomNavigationScreens.AddBook.route) {
            AddBookScreen()
        }
        composable(BottomNavigationScreens.Goals.route) {
            GoalsScreen()
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingsScreen(mainNavController, themeViewModel)
        }
    }
}


@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Row{
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val currentRoute = currentRoute(navController)
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Box(modifier =
                            if (currentRoute == screen.route) {
                                Modifier.background(MaterialTheme.colorScheme.background)
                            }
                            else {
                                Modifier.background(MaterialTheme.colorScheme.primary)
                            }
                            ){
                                Icon(
                                    screen.icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        },
                        label = {
                            Text(stringResource(id = screen.resourceId),
                                fontFamily = getFontFamily(),
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(top = 2.dp))
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.background
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