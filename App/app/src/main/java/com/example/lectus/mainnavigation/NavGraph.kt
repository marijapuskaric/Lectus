package com.example.lectus.mainnavigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.example.lectus.composables.MainScreen
import com.example.lectus.screens.LoginScreen
import com.example.lectus.screens.RegisterScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController
) {
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
                onNavRegisterPage = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }
        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(
                onNavLoginPage = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen()
        }
    }
}