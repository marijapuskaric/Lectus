package com.example.lectus.mainnavigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lectus.R

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object MyLibrary : BottomNavigationScreens("MyLibrary", R.string.my_library_route, Icons.Filled.LibraryBooks)
    object AddBook : BottomNavigationScreens("AddBook", R.string.add_book_route, Icons.Filled.Add)
    object Goals : BottomNavigationScreens("Goals", R.string.goals_route, Icons.Filled.AccessAlarm)
    object Settings : BottomNavigationScreens("Settings", R.string.settings_route, Icons.Filled.Settings)
}
