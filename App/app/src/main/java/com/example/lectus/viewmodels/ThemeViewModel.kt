package com.example.lectus.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme
    private val _themeChanged = mutableStateOf(false)
    val themeChanged: State<Boolean> = _themeChanged

    fun setDarkTheme(value: Boolean){
        _isDarkTheme.value = value
        _themeChanged.value = true
    }
    fun resetThemeChanged() {
        _themeChanged.value = false
    }
}