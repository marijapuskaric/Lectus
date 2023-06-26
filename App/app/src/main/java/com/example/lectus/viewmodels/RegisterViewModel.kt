package com.example.lectus.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lectus.authentication.AuthRepository
import com.example.lectus.authentication.Response
import com.example.lectus.authentication.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var registerResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    fun signUpWithEmailAndPassword(email: String, password: String, username: String) = viewModelScope.launch {
        registerResponse = Response.Loading
        registerResponse = repo.firebaseSignUpWithEmailAndPassword(email, password, username)
    }
}