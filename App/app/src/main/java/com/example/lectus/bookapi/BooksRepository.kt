package com.example.lectus.bookapi

import com.example.lectus.data.API_KEY
import com.example.lectus.data.Book
import com.example.lectus.data.SearchResponse
import retrofit2.Response

class BooksRepository
{
    private var isLoading = false
    suspend fun getBooksList(query: String, startIndex: Int): List<Book>
    {
        isLoading = true
        val response = getResponse(query,startIndex)
        return if (!isLoading)
        {
            val searchResponse = response.body()
            searchResponse?.items ?: emptyList()
        }
        else
        {
            isLoading = false
            emptyList()
        }
    }
    private suspend fun getResponse(query: String,startIndex: Int): Response<SearchResponse>
    {
        val retrofit = RetrofitClient.getInstance()
        val bookApi = retrofit.create(BookApi::class.java)
        val response = bookApi.getBooks(
            query = query,
            startIndex = startIndex,
            maxResults = 20,
            apiKey = API_KEY,
        )
        return if (response.isSuccessful)
        {
            isLoading = false
            response
        } else
        {
            isLoading = true
            response
        }
    }
}

