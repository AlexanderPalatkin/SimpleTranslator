package com.example.simpletranslator.model.data.api

import com.example.simpletranslator.model.data.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>

    @GET("words/search")
    suspend fun search(@Query("search") wordToSearch: String): List<DataModel>
}
