package com.example.lectus.composables

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lectus.getFontFamily
import com.example.lectus.data.BottomNavigationScreens


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun CustomBottomNavigation(mainNavController: NavHostController) {
    val navItems = listOf(
        BottomNavigationScreens.MyLibrary,
        BottomNavigationScreens.AddBook,
        BottomNavigationScreens.Goals,
        BottomNavigationScreens.Settings
    )
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    val currentRoute = currentRoute(mainNavController)
                    navItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Box(
                                    modifier = if (currentRoute?.startsWith(screen.route) == true) {
                                        Modifier.background(MaterialTheme.colorScheme.background)
                                    } else {
                                        Modifier.background(MaterialTheme.colorScheme.primary)
                                    }
                                ) {
                                    Icon(
                                        screen.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            },
                            label = {
                                Text(
                                    stringResource(id = screen.resourceId),
                                    fontFamily = getFontFamily(),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    mainNavController.navigate(screen.route) {
                                        popUpTo(currentRoute.orEmpty()) {
                                            inclusive = true
                                        }
                                    }
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

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}