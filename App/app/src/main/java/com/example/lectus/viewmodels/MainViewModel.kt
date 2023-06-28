package com.example.lectus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lectus.authentication.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel()
{
    init {
        getAuthState()
    }
    fun getAuthState() = repo.getAuthState(viewModelScope)
}