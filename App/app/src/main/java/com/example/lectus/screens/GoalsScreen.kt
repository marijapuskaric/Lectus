package com.example.lectus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.lectus.R

import com.example.lectus.composables.Header

@Composable
fun GoalsScreen() {
    Column(modifier = Modifier.background(colorResource(id = R.color.champagne)).fillMaxSize())
    {
        Header()
    }

}