package com.example.lectus.mainnavigation

import androidx.compose.ui.res.stringResource
import com.example.lectus.R

sealed class Screen(val route: String) {
    object LoginScreen: Screen("Login")
    object RegisterScreen: Screen("Register")
    object MainScreen: Screen("MainScreen")
}