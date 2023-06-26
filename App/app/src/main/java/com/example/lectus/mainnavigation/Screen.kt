package com.example.lectus.mainnavigation


sealed class Screen(val route: String) {
    object LoginScreen: Screen("Login")
    object RegisterScreen: Screen("Register")
    object MainScreen: Screen("MainScreen")

}