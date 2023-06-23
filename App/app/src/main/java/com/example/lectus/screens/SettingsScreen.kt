package com.example.lectus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.R
import com.example.lectus.composables.Header
import com.example.lectus.viewmodels.SettingsViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(Modifier.background(colorResource(id = R.color.champagne)))
    {
        Header()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column() {
                Text(
                    text = "Settings",
                    fontSize = 24.sp
                )
                Button(
                    onClick = {viewModel.signOut()},
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.redwood)),
                    ){
                    Text(text = "sign out")
                }
            }

        }
    }

}