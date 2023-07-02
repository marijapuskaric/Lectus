package com.example.lectus.bookapi

import com.example.lectus.data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface BookApi
{
    @GET("volumes")
    suspend fun getBooks(
        @Query(value = "q") query: String?,
        @Query(value = "startIndex") startIndex: Int,
        @Query(value = "maxResults") maxResults: Int,
    ): Response<SearchResponse>
}