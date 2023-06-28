package com.example.lectus.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lectus.composables.ProgressBar
import com.example.lectus.data.Response
import com.example.lectus.viewmodels.LoginViewModel

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val loginResponse = viewModel.loginResponse)
    {
        is Response.Loading -> ProgressBar()
        is Response.Success -> Unit
        is Response.Failure -> loginResponse.apply {
            LaunchedEffect(e)
            {
                Utils.print(e)
                showErrorMessage(e.message)
            }
        }
    }
}