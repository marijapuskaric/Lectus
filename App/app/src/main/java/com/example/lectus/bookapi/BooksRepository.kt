package com.example.lectus.bookapi

import com.example.lectus.data.Book
import com.example.lectus.data.SearchResponse
import retrofit2.Response

class BooksRepository {
    var isLoading = false
    suspend fun getBooksList(query: String, startIndex: Int): List<Book>{
        isLoading = true
        var response = getResponse(query,startIndex)
        if (isLoading == false) {
            val searchResponse = response.body()
            if (searchResponse != null) {
                val books = searchResponse.items
                return books
            } else {
                return emptyList()
            }
        }
        else{
            isLoading = false
            return emptyList()
        }
    }
    suspend fun getResponse(query: String,startIndex: Int): Response<SearchResponse>
    {
        val retrofit = RetrofitClient.getInstance()
        val bookApi = retrofit.create(BookApi::class.java)
        val response = bookApi.getBooks(
            query = query,
            startIndex = startIndex,
            maxResults = 20,
            apiKey = "AIzaSyCmpziL380ybxxeRruTmMuyPlGM5uL_00o",
        )
        if (response.isSuccessful)
        {
            isLoading = false
            return response
        }
        else{
            isLoading = true
            return response
        }
    }
}

