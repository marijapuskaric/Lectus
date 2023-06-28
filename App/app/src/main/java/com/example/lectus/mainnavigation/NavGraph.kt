package com.example.lectus.mainnavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.example.lectus.data.BottomNavigationScreens
import com.example.lectus.data.Screen
import com.example.lectus.screens.AddBookScreen
import com.example.lectus.screens.GoalsScreen
import com.example.lectus.screens.LoginScreen
import com.example.lectus.screens.MyLibraryScreen
import com.example.lectus.screens.RegisterScreen
import com.example.lectus.screens.SettingsScreen
import com.example.lectus.viewmodels.ThemeViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    val themeChanged = themeViewModel.themeChanged.value
    if (themeChanged)
    {
        LaunchedEffect(key1 = themeChanged)
        {
            navController.navigate(BottomNavigationScreens.Settings.route)
            {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
            themeViewModel.resetThemeChanged()
        }
    }
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
        enterTransition = {EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(
                onNavRegisterPage =
                {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }
        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(
                onNavLoginPage =
                {
                    navController.popBackStack()
                }
            )
        }
        composable(route = BottomNavigationScreens.Settings.route)
        {
            SettingsScreen(mainNavController = navController, themeViewModel = themeViewModel)
        }
        composable(route = BottomNavigationScreens.MyLibrary.route)
        {
            MyLibraryScreen(mainNavController = navController)
        }
        composable(route = BottomNavigationScreens.AddBook.route)
        {
            AddBookScreen(mainNavController = navController)
        }
        composable(route = BottomNavigationScreens.Goals.route)
        {
            GoalsScreen(mainNavController = navController)
        }
    }
}