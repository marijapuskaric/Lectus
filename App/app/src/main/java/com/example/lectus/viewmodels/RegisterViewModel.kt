package com.example.lectus.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lectus.authentication.AuthRepository
import com.example.lectus.data.Response
import com.example.lectus.authentication.SignUpResponse
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel()
{
    var registerResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    fun signUpWithEmailAndPassword(email: String, password: String, username: String, context: Context, db: FirebaseFirestore) = viewModelScope.launch {
        registerResponse = repo.firebaseSignUpWithEmailAndPassword(email, password, username, context, db)
    }
}