package com.example.repository.datasource

import com.example.model.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    suspend fun search(@Query("search") wordToSearch: String): List<SearchResultDto>
}