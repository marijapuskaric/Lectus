package com.example.lectus.viewmodels


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lectus.authentication.AuthRepository
import com.example.lectus.authentication.ReloadUserResponse
import com.example.lectus.authentication.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    fun signOut() = repo.signOut()
}