package com.example.lectus.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lectus.bookapi.BooksRepository
import com.example.lectus.data.Book
import kotlinx.coroutines.launch


class SearchBookViewModel(private val repo: BooksRepository): ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books : LiveData<List<Book>>
        get() = _books
    fun getBooksList(query: String){
        viewModelScope.launch {
            _books.value = repo.getBooksList(query, 0)
            Log.d("SEARCH VIEW MODEL", books.toString())
        }
    }




}