package com.example.lectus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.lectus.authentication.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    suspend fun resetPassword(email: String) = repo.sendPasswordResetEmail(email)
    fun signOut() = repo.signOut()
}