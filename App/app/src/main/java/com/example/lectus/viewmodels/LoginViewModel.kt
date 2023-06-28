package com.example.lectus.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lectus.authentication.AuthRepository
import com.example.lectus.data.Response
import com.example.lectus.authentication.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel()
{
    var loginResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set
    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        loginResponse = Response.Loading
        loginResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
}