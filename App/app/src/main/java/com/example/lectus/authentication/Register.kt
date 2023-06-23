package com.example.lectus.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.screens.ProgressBar
import com.example.lectus.viewmodels.RegisterViewModel

@Composable
fun Register(
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    when(val registerResponse = viewModel.registerResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> Unit
        is Response.Failure -> registerResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}