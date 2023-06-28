package com.example.lectus.data


sealed class Screen(val route: String)
{
    object LoginScreen: Screen("Login")
    object RegisterScreen: Screen("Register")
}